package com.taetae98.oauth.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.people.v1.PeopleServiceScopes
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.taetae98.oauth.R
import com.taetae98.oauth.base.BaseFragment
import com.taetae98.oauth.databinding.FragmentLoginBinding
import com.taetae98.oauth.utility.DataBinding
import com.taetae98.oauth.viewmodel.MainActivityViewModel
import com.taetae98.oauth.viewmodel.MainActivityViewModel.Companion.GOOGLE
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.concurrent.thread


class LoginFragment : BaseFragment(), DataBinding<FragmentLoginBinding> {
    override val binding by lazy { DataBinding.get<FragmentLoginBinding>(this, R.layout.fragment_login) }

    private val viewModel by activityViewModels<MainActivityViewModel>()

    companion object {
        private const val GOOGLE_LOGIN = 1000
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            GOOGLE_LOGIN -> {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.result!!

                    updateViewModelWithGoogleAccount(account)
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onCreateDataBinding()
        onCreateOnSigninWithGoogle()
        onCreateOnSigninWithKakao()
        onCreateOnSigninWithNaver()

        return binding.root
    }

    private fun onCreateDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateOnSigninWithGoogle() {
        binding.setOnSigninWithGoogle {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestScopes(
                            Scope(PeopleServiceScopes.USER_BIRTHDAY_READ),
                            Scope(PeopleServiceScopes.USER_GENDER_READ)
                    )
                    .requestServerAuthCode(getString(R.string.google_client_id))
                    .build()

            val client = GoogleSignIn.getClient(requireActivity(), gso)

            val signInIntent: Intent = client.signInIntent
            startActivityForResult(signInIntent, GOOGLE_LOGIN)
        }
    }

    private fun onCreateOnSigninWithKakao() {
        binding.setOnSigninWithKakao {
            val callback: (OAuthToken?, Throwable?) -> Unit = callback@{ _, error1 ->
                if (error1 != null) {
                    Log.e("PASS", "로그인 실패", error1)
                    return@callback
                }

                val scopes = listOf("account_email", "birthday", "gender")
                UserApiClient.instance.loginWithNewScopes(requireContext(), scopes) { _, error2 ->
                    if (error2 != null) {
                        Log.e("PASS", "사용자 추가 정보 획득 로그인 실패", error2)
                        return@loginWithNewScopes
                    }

                    UserApiClient.instance.me { user, error3 ->
                        if (error3 != null) {
                            Log.e("PASS", "사용자 추가 정보 획득 실패", error3)
                            return@me
                        }

                        with(viewModel) {
                            email = user?.kakaoAccount?.email
                            gender = user?.kakaoAccount?.gender?.name
                            birth = null
                            name = user?.kakaoAccount?.profile?.nickname
                            type = "Kakao"
                        }

                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
                    }
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
            }
        }
    }

    private fun onCreateOnSigninWithNaver() {
        binding.setOnSigninWithNaver {
            with(OAuthLogin.getInstance()) {
                init(requireContext(), getString(R.string.naver_client_id), getString(R.string.naver_client_secret), "OAuth")
                startOauthLoginActivity(requireActivity(), NaverHandler {
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = requestApi(requireContext(), getAccessToken(requireContext()), "https://openapi.naver.com/v1/nid/me")
                        Log.d("PASS", response)
                        val jsonObject = JSONObject(response).getJSONObject("response")
                        with(viewModel) {
                            name = jsonObject.getString("name")
                            gender = jsonObject.getString("gender")
                            birth = jsonObject.getString("birthyear") + jsonObject.getString("birthday")
                            email = jsonObject.getString("email")
                            type = MainActivityViewModel.NAVER
                        }

                        withContext(Dispatchers.Main) {
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
                        }
                    }
                })
            }
        }
    }

    private fun updateViewModelWithGoogleAccount(account: GoogleSignInAccount) {
        val transport = NetHttpTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()

        runBlocking(Dispatchers.IO) {
            val response = GoogleAuthorizationCodeTokenRequest(
                    transport,
                    jsonFactory,
                    getString(R.string.google_client_id),
                    getString(R.string.google_client_secret),
                    account.serverAuthCode,
                    ""
            ).execute()

            (URL("https://people.googleapis.com/v1/people/${account.id}?personFields=birthdays,genders&key=${getString(R.string.google_api_key)}").openConnection() as HttpsURLConnection).apply {
                setRequestProperty("Authorization", "Bearer ${response.accessToken}")

                inputStream.bufferedReader().use {
                    val json = JSONObject(it.readText())
                    val genderJson = json.getJSONArray("genders").getJSONObject(0)
                    val birthJson = json.getJSONArray("birthdays").getJSONObject(0).getJSONObject("date")

                    with(viewModel) {
                        email = account.email
                        name = account.displayName
                        gender = genderJson.getString("value")
                        birth = "${birthJson.getString("year")} / ${birthJson.getString("month")} / ${birthJson.getString("day")}"
                        type = GOOGLE
                    }
                }
            }
        }
    }

    private class NaverHandler(private val callback: ()->Unit) : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                Log.d("PASS", "Naver Success")
                callback.invoke()
            } else {
                Log.e("PASS", "Naver Fail")
            }
        }
    }
}