package com.taetae98.googleapi.oauth

import android.content.Context
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.taetae98.googleapi.R
import com.taetae98.googleapi.application.TAG
import com.taetae98.googleapi.manager.AccountManager
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class GoogleOAuth @Inject constructor(
    private val fragment: Fragment,
    private val accountManager: AccountManager
) {
    private val context: Context
        get() {
            return fragment.requireContext()
        }

    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_web_client_id))
            .requestEmail()
            .build()
    }

    private val client by lazy {
        GoogleSignIn.getClient(context, gso)
    }

    private val signInResult = fragment.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            val account = task.getResult(ApiException::class.java)

            Log.d(TAG, "Google SignIn : $account")
            accountManager.onSignInWithGoogle(account.idToken, callback!!)
        } catch (e: Exception) {
            Log.e(TAG, "Google SignIn Fail", e)
            callback?.onFail()
        }
    }

    private var callback: AccountManager.OnSignInCallback? = null

    fun signIn(callback: AccountManager.OnSignInCallback) {
        this.callback = callback
        signInResult.launch(client.signInIntent)
    }
}