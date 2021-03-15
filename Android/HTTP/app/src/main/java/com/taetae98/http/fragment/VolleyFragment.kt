package com.taetae98.http.fragment

import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.taetae98.http.R
import com.taetae98.http.base.BaseFragment
import com.taetae98.http.databinding.FragmentVolleyBinding
import com.taetae98.http.singleton.Server
import com.taetae98.http.singleton.VolleyHTTP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.concurrent.thread

class VolleyFragment : BaseFragment<FragmentVolleyBinding>(R.layout.fragment_volley) {
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
        initOnSyncSubmit()
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
            when (selectedContentType) {
                "application/x-www-form-urlencoded" -> {
                    urlencoded()
                }

                "application/json" -> {
                    json()
                }
            }
        }
    }

    private fun initOnSyncSubmit() {
        binding.setOnSyncSubmit {
            syncVolley()
        }
    }

    private fun urlencoded() {
        val method = when (selectedMethod) {
            "GET" -> {
                Request.Method.GET
            }
            "POST" -> {
                Request.Method.POST
            }
            else -> {
                throw Exception("Unsupported Method")
            }
        }

        val url = when (selectedMethod) {
            "GET" -> {
                "${getURL()}?${getParameter()}"
            }
            "POST" -> {
                getURL()
            }
            else -> {
                throw Exception("Unsupported Method")
            }
        }

        val request = object : StringRequest(method, url,
                {
                    binding.resultTextView.text = it
                },
                {
                    binding.resultTextView.text = it.toString()
                }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                Log.d("PASS", "Call getHeaders($selectedContentType, $selectedMethod)")
                return HashMap<String, String>(super.getHeaders()).apply {
                    put("Content-Type", "application/x-www-form-urlencoded")
                }
            }

            override fun getParams(): MutableMap<String, String>? {
                Log.d("PASS", "Call getParams($selectedContentType, $selectedMethod)")
                return if (method == Method.POST) {
                    val parameter1 = binding.parameter1InputLayout.editText!!.text.toString()
                    val parameter2 = binding.parameter2InputLayout.editText!!.text.toString()

                    HashMap<String, String>().apply {
                        put("parameter1", parameter1)
                        put("parameter2", parameter2)
                    }
                } else {
                    super.getParams()
                }
            }
        }

        VolleyHTTP.getInstance(requireContext()).request(request)
    }

    private fun json() {
        val method = when (selectedMethod) {
            "GET" -> {
                Request.Method.GET
            }
            "POST" -> {
                Request.Method.POST
            }
            else -> {
                throw Exception("Unsupported Method")
            }
        }

        val parameter1 = binding.parameter1InputLayout.editText!!.text.toString()
        val parameter2 = binding.parameter2InputLayout.editText!!.text.toString()
        val parameters = JSONObject(hashMapOf<Any?, Any?>("parameter1" to parameter1, "parameter2" to parameter2))

        val request = JsonObjectRequest(method, getURL(), parameters,
                {
                    binding.resultTextView.text = it.toString()
                },
                {
                    binding.resultTextView.text = it.toString()
                }
        )

        VolleyHTTP.getInstance(requireContext()).request(request)
    }

    private fun syncVolley() {
        val parameter1 = binding.parameter1InputLayout.editText!!.text.toString()
        val parameter2 = binding.parameter2InputLayout.editText!!.text.toString()
        val parameters = JSONObject(hashMapOf<Any?, Any?>("parameter1" to parameter1, "parameter2" to parameter2))

        val requestFuture = RequestFuture.newFuture<JSONObject>()

        val request = object : JsonObjectRequest(Method.POST, "${Server.PROTOCOL}://${Server.IP}:${Server.PORT}/application/json", parameters, requestFuture, requestFuture) {
            override fun getHeaders(): MutableMap<String, String> {
                return HashMap<String, String>(super.getHeaders()).apply {
                    put("Content-Type", "application/json")
                }
            }
        }.apply {
            // timeOutMs += timeOutMs * backoffMultiplier
            retryPolicy = DefaultRetryPolicy(1000, 2, 3.0F)
        }

        VolleyHTTP.getInstance(requireContext()).request(request)

        thread {
            val jsonObject = requestFuture.get()
            CoroutineScope(Dispatchers.Main).launch {
                binding.resultTextView.text = jsonObject.toString()
            }
        }
    }

    private fun getParameter(): String {
        val parameter1 = binding.parameter1InputLayout.editText?.text.toString()
        val parameter2 = binding.parameter2InputLayout.editText?.text.toString()

        return if (selectedContentType == "application/x-www-form-urlencoded") {
            "parameter1=$parameter1&parameter2=$parameter2"
        } else {
            "{\"parameter1\":\"$parameter1\",\"parameter2\":\"$parameter2\"}"
        }
    }

    private fun getURL(): String {
        return "${Server.PROTOCOL}://${Server.IP}:${Server.PORT}/$selectedContentType"
    }
}