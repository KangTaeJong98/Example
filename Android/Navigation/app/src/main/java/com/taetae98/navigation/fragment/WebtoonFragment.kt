package com.taetae98.navigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseFragment
import com.taetae98.navigation.view.WebtoonWebView

abstract class WebtoonFragment<VB: ViewDataBinding>(layoutId: Int) : BaseFragment<VB>(layoutId) {
    protected abstract val baseURL: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        onCreateOnBackPressCallback()
        return view
    }

    private fun onCreateOnBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            with(binding.root.findViewById<WebtoonWebView>(R.id.web_view)) {
                if (canGoBack()) {
                    goBack()
                } else {
                    findNavController().navigateUp()
                }
            }
        }
    }
}