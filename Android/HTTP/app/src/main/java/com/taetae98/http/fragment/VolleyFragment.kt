package com.taetae98.http.fragment

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.taetae98.http.R
import com.taetae98.http.base.BaseFragment
import com.taetae98.http.databinding.FragmentVolleyBinding
import com.taetae98.http.singleton.VolleyHTTP

class VolleyFragment : BaseFragment<FragmentVolleyBinding>(R.layout.fragment_volley) {
    override fun init() {
        super.init()
        VolleyHTTP.getInstance(requireContext()).request<String>(StringRequest(
            Request.Method.GET, "https://www.google.com/",
            {
                binding.response = it
            },
            {
                binding.response = it.toString()
            }
        ))
    }
}