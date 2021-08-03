package com.taetae98.constraintlayout.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taetae98.constraintlayout.R
import com.taetae98.constraintlayout.databinding.BindingFragment
import com.taetae98.constraintlayout.databinding.FragmentPlaceBinding

class PlaceFragment : BindingFragment<FragmentPlaceBinding>(R.layout.fragment_place) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateOnVisible()
        onCreateOnGone()

        return binding.root
    }

    private fun onCreateOnVisible() {
        binding.setOnVisible { 
            binding.visible = View.VISIBLE
        }
    }
    
    private fun onCreateOnGone() {
        binding.setOnGone {
            binding.visible = View.GONE
        }
    }
}