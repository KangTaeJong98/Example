package com.taetae98.googleapi.manager

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.taetae98.googleapi.application.TAG
import com.taetae98.googleapi.oauth.GoogleOAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountManager @Inject constructor() {
    fun onSignInWithGoogle(idToken: String?, callback: OnSignInCallback) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnSuccessListener {
            Log.d(TAG, "Firebase OAuth : $it")
            callback.onSuccess()
        }.addOnFailureListener {
            Log.e(TAG, "Firebase OAuth Fail", it)
            callback.onFail()
        }
    }

    interface OnSignInCallback {
        fun onSuccess()
        fun onFail()
    }
}