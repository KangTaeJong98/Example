package com.taetae98.navigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taetae98.navigation.R
import com.taetae98.navigation.databinding.FragmentNaverBinding

class NaverFragment : WebtoonFragment<FragmentNaverBinding>(R.layout.fragment_naver) {
    override val baseURL: String = "https://comic.naver.com/index.nhn"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateWebView()
        return view
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateWebView() {
        with(binding.webView) {
            loadUrl(baseURL)
        }
    }
}