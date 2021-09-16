package com.taetae98.googleapi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taetae98.googleapi.R
import com.taetae98.googleapi.databinding.BindingFragment
import com.taetae98.googleapi.databinding.FragmentSignInBinding
import com.taetae98.googleapi.manager.AccountManager
import com.taetae98.googleapi.oauth.GoogleOAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BindingFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    private val onGoogleSignInCallback by lazy {
        object : AccountManager.OnSignInCallback {
            override fun onSuccess() {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMapFragment())
            }

            override fun onFail() {
                findNavController().navigate(SignInFragmentDirections.actionGlobalLocationAddDialog())
            }
        }
    }

    @Inject
    lateinit var googleOAuth: GoogleOAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateOnGoogleSignIn()

        return binding.root
    }

    private fun onCreateOnGoogleSignIn() {
        binding.setOnGoogleSignIn {
            googleOAuth.signIn(onGoogleSignInCallback)
        }
    }
}