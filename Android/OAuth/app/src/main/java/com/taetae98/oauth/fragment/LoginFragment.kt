package com.taetae98.oauth.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.api.client.googleapis.auth.oauth2.*
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.people.v1.PeopleServiceScopes
import com.taetae98.oauth.viewmodel.MainActivityViewModel
import com.taetae98.oauth.viewmodel.MainActivityViewModel.Companion.GOOGLE
import com.taetae98.oauth.R
import com.taetae98.oauth.base.BaseFragment
import com.taetae98.oauth.databinding.FragmentLoginBinding
import com.taetae98.oauth.utility.DataBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection


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

    override fun onStart() {
        super.onStart()
//        GoogleSignIn.getLastSignedInAccount(requireContext())?.let {
//            updateViewModelWithGoogleAccount(it)
//            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onCreateDataBinding()
        onCreateOnGoogleLogin()

        return binding.root
    }

    private fun onCreateDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateOnGoogleLogin() {
        binding.setOnSigninWithGoogle {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestScopes(
                            Scope(PeopleServiceScopes.USER_BIRTHDAY_READ),
                            Scope(PeopleServiceScopes.USER_GENDER_READ)
                    )
                    .requestServerAuthCode(getString(R.string.client_id))
                    .build()

            val client = GoogleSignIn.getClient(requireActivity(), gso)

            val signInIntent: Intent = client.signInIntent
            startActivityForResult(signInIntent, GOOGLE_LOGIN)
        }
    }

    private fun updateViewModelWithGoogleAccount(account: GoogleSignInAccount) {
        val transport = NetHttpTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()

        runBlocking(Dispatchers.IO) {
            val response = GoogleAuthorizationCodeTokenRequest(
                    transport,
                    jsonFactory,
                    getString(R.string.client_id),
                    getString(R.string.client_secret),
                    account.serverAuthCode,
                    ""
            ).execute()

            (URL("https://people.googleapis.com/v1/people/${account.id}?personFields=birthdays,genders&key=${getString(R.string.oauth_key)}").openConnection() as HttpsURLConnection).apply {
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
}