package com.taetae98.bridge.fragment

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.taetae98.bridge.R
import com.taetae98.bridge.WEB_VIEW_HOME
import com.taetae98.bridge.databinding.BindingFragment
import com.taetae98.bridge.databinding.FragmentWebViewBinding

class WebViewFragment : BindingFragment<FragmentWebViewBinding>(R.layout.fragment_web_view) {
    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_web_view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.code -> {
                binding.webView.postGetHTMLCode {
                    findNavController().navigate(WebViewFragmentDirections.actionWebViewFragmentToCodeFragment(it))
                }
                true
            }
            R.id.home -> {
                binding.url = WEB_VIEW_HOME
                true
            }
            else -> {
                false
            }
        }
    }
}