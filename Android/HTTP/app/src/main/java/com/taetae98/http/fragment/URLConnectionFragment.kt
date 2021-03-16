package com.taetae98.http.fragment

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.taetae98.http.R
import com.taetae98.http.base.BaseFragment
import com.taetae98.http.databinding.FragmentUrlConnectionBinding
import com.taetae98.http.singleton.Server
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class URLConnectionFragment : BaseFragment<FragmentUrlConnectionBinding>(R.layout.fragment_url_connection) {
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
            when (selectedMethod) {
                "GET" -> {
                    get()
                }

                "POST" -> {
                    post()
                }
            }
        }
    }

    private fun get() {
        when (selectedContentType) {
            "application/x-www-form-urlencoded" -> {
                getUrlencoded()
            }

            "application/json" -> {
                getJson()
            }
        }
    }

    private fun getUrlencoded() {
        thread {
            (URL("${getURL()}?${getParameter()}").openConnection() as HttpURLConnection).apply {
                doInput = true
                doOutput = false
                requestMethod = "GET"
                connectTimeout = 3000
                readTimeout = 3000
                setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                try {
                    inputStream.bufferedReader().use {
                        val result = it.readText()
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.resultTextView.text = result
                        }
                    }
                } catch (e: Exception) {
                    val result = "${this.responseCode} : $responseMessage"
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.resultTextView.text = result
                    }
                }

                disconnect()
            }
        }
    }

    private fun getJson() {
        thread {
            (URL(getURL()).openConnection() as HttpURLConnection).apply {
                doInput = true
                doOutput = true
                requestMethod = "GET"
                connectTimeout = 3000
                readTimeout = 3000
                setRequestProperty("Content-Type", "application/json")

                outputStream.bufferedWriter().use {
                    it.write(getParameter())
                }

                try {
                    inputStream.bufferedReader().use {
                        val result = it.readText()
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.resultTextView.text = result
                        }
                    }
                } catch (e: Exception) {
                    val result = "${this.responseCode} : $responseMessage"
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.resultTextView.text = result
                    }
                }

                disconnect()
            }
        }
    }

    private fun post() {
        thread {
            (URL(getURL()).openConnection() as HttpURLConnection).apply {
                doInput = true
                doOutput = true
                requestMethod = "POST"
                connectTimeout = 3000
                readTimeout = 3000
                setRequestProperty("Content-Type", selectedContentType)

                outputStream.bufferedWriter().use {
                    it.write(getParameter())
                }

                try {
                    inputStream.bufferedReader().use {
                        val result = it.readText()
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.resultTextView.text = result
                        }
                    }
                } catch (e: Exception) {
                    val result = "${this.responseCode} : $responseMessage"
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.resultTextView.text = result
                    }
                }

                disconnect()
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