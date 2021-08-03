package com.taetae98.constraintlayout.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.taetae98.constraintlayout.R
import com.taetae98.constraintlayout.databinding.BindingFragment
import com.taetae98.constraintlayout.databinding.FragmentRatioBinding

class RatioFragment : BindingFragment<FragmentRatioBinding>(R.layout.fragment_ratio) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateOnOneToOne()
        onCreateOnFourToThree()
        onCreateOnThreeToFour()

        return binding.root
    }

    private fun onCreateOnOneToOne() {
        with(binding) {
            setOnOneToOne {
                textView.text = "1:1"
                (textView.layoutParams as ConstraintLayout.LayoutParams).apply {
                    dimensionRatio = "1:1"
                }.also {
                    textView.layoutParams = it
                }
            }
        }
    }

    private fun onCreateOnFourToThree() {
        with(binding) {
            setOnFourToThree {
                textView.text = "4:3"
                (textView.layoutParams as ConstraintLayout.LayoutParams).apply {
                    dimensionRatio = "4:3"
                }.also {
                    textView.layoutParams = it
                }
            }
        }
    }

    private fun onCreateOnThreeToFour() {
        with(binding) {
            setOnThreeToFour {
                textView.text = "3:4"
                (textView.layoutParams as ConstraintLayout.LayoutParams).apply {
                    dimensionRatio = "3:4"
                }.also {
                    textView.layoutParams = it
                }
            }
        }
    }
}