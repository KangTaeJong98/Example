package com.taetae98.databinding.fragment

import androidx.navigation.fragment.navArgs
import com.taetae98.databinding.R
import com.taetae98.databinding.base.BaseFragment
import com.taetae98.databinding.databinding.FragmentInformationBinding

class InformationFragment : BaseFragment<FragmentInformationBinding>(R.layout.fragment_information) {
    private val args by navArgs<InformationFragmentArgs>()

    override fun init() {
        super.init()
        initMovie()
    }

    private fun initMovie() {
        binding.movie = args.movie
    }
}