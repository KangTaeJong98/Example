package com.taetae98.http.fragment

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.taetae98.http.R
import com.taetae98.http.base.BaseFragment
import com.taetae98.http.databinding.FragmentRetrofitBinding

class RetrofitFragment : BaseFragment<FragmentRetrofitBinding>(R.layout.fragment_retrofit) {
    private val methods = arrayOf("GET", "POST")
    private val contentTypes = arrayOf("application/x-www-form-urlencoded", "application/json")

    private var selectedMethod = methods.first()
    private var selectedContentType = contentTypes.first()

    override fun init() {
        super.init()
        initSupportActionBar()
        initMethodAutoCompleteTextView()
        initContentTypeAutoCompleteTextView()
        initOnSubmit()
    }

    private fun initSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initMethodAutoCompleteTextView() {
        with(binding.methodInputLayout.editText as MaterialAutoCompleteTextView) {
            setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, methods))
            setText(methods.first(), false)

            onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedMethod = methods[position]
                Toast.makeText(requireContext(), selectedMethod, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initContentTypeAutoCompleteTextView() {
        with(binding.contentTypeInputLayout.editText as MaterialAutoCompleteTextView) {
            setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, contentTypes))
            setText(contentTypes.first(), false)

            onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedContentType = contentTypes[position]
                Toast.makeText(requireContext(), selectedContentType, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initOnSubmit() {
        binding.setOnSubmit {

        }
    }
}