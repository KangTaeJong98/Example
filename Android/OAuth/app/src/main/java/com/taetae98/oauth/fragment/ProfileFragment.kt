package com.taetae98.oauth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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

        return binding.root
    }

    private fun onCreateDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}