package com.taetae98.oauth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.people.v1.PeopleServiceScopes
import com.taetae98.oauth.R
import com.taetae98.oauth.base.BaseFragment
import com.taetae98.oauth.databinding.FragmentProfileBinding
import com.taetae98.oauth.utility.DataBinding
import com.taetae98.oauth.viewmodel.MainActivityViewModel

class ProfileFragment : BaseFragment(), DataBinding<FragmentProfileBinding> {
    override val binding by lazy { DataBinding.get<FragmentProfileBinding>(this, R.layout.fragment_profile) }

    private val viewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onCreateDataBinding()
        onCreateOnLogout()

        return binding.root
    }

    private fun onCreateDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun onCreateOnLogout() {
        binding.setOnLogout {
            when(viewModel.type) {
                MainActivityViewModel.GOOGLE -> {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .build()

                    val client = GoogleSignIn.getClient(requireActivity(), gso)

                    client.signOut().addOnCompleteListener {
                        findNavController().navigateUp()
                    }
                }
            }

            viewModel.reset()
        }
    }
}