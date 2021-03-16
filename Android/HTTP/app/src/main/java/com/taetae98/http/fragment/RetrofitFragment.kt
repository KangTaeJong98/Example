package com.taetae98.http.fragment

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.taetae98.http.R
import com.taetae98.http.base.BaseFragment
import com.taetae98.http.databinding.FragmentRetrofitBinding
import com.taetae98.http.dto.RequestResult
import com.taetae98.http.singleton.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            val parameter1 = binding.parameter1InputLayout.editText!!.text.toString()
            val parameter2 = binding.parameter2InputLayout.editText!!.text.toString()

            when (selectedContentType) {
                "application/x-www-form-urlencoded" -> {
                    when (selectedMethod) {
                        "GET" -> {
//                            비동기
                            RetrofitBuilder.retrofit.getUrlencoded(parameter1, parameter2).enqueue(object : Callback<RequestResult> {
                                override fun onResponse(call: Call<RequestResult>, response: Response<RequestResult>) {
                                    if (response.isSuccessful) {
                                        binding.resultTextView.text = response.body()?.toString()
                                    } else {
                                        binding.resultTextView.text = response.errorBody()?.toString()
                                    }
                                }

                                override fun onFailure(call: Call<RequestResult>, t: Throwable) {
                                    binding.resultTextView.text = t.toString()
                                }
                            })

//                            동기
//                            thread {
//                                val response = RetrofitBuilder.retrofit.getUrlencoded(parameter1, parameter2).execute()
//                                CoroutineScope(Dispatchers.Main).launch {
//                                    if (response.isSuccessful) {
//                                        binding.resultTextView.text = response.body()?.toString()
//                                    } else {
//                                        binding.resultTextView.text = response.errorBody()?.toString()
//                                    }
//                                }
//                            }
                        }

                        "POST" -> {
                            RetrofitBuilder.retrofit.postUrlencoded(hashMapOf("parameter1" to parameter1, "parameter2" to parameter2)).enqueue(object : Callback<RequestResult> {
                                override fun onResponse(call: Call<RequestResult>, response: Response<RequestResult>) {
                                    binding.resultTextView.text = response.body()?.toString()
                                }

                                override fun onFailure(call: Call<RequestResult>, t: Throwable) {
                                    binding.resultTextView.text = t.toString()
                                }
                            })
                        }
                    }
                }

                "application/json" -> {
                    when (selectedMethod) {
                        "GET" -> {
                            RetrofitBuilder.retrofit.getJson(hashMapOf("parameter1" to parameter1, "parameter2" to parameter2)).enqueue(object : Callback<RequestResult> {
                                override fun onResponse(call: Call<RequestResult>, response: Response<RequestResult>) {
                                    binding.resultTextView.text = response.body()?.toString()
                                }

                                override fun onFailure(call: Call<RequestResult>, t: Throwable) {
                                    binding.resultTextView.text = t.toString()
                                }
                            })
                        }

                        "POST" -> {
                            RetrofitBuilder.retrofit.postJson(hashMapOf("parameter1" to parameter1, "parameter2" to parameter2)).enqueue(object : Callback<RequestResult> {
                                override fun onResponse(call: Call<RequestResult>, response: Response<RequestResult>) {
                                    binding.resultTextView.text = response.body()?.toString()
                                }

                                override fun onFailure(call: Call<RequestResult>, t: Throwable) {
                                    binding.resultTextView.text = t.toString()
                                }
                            })
                        }
                    }
                }
            }

        }
    }
}