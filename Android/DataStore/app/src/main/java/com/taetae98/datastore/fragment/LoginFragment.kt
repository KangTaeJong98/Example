package com.taetae98.datastore.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.taetae98.datastore.R
import com.taetae98.datastore.api.SmartGachonAPI
import com.taetae98.datastore.base.BaseFragment
import com.taetae98.datastore.databinding.FragmentLoginBinding
import com.taetae98.datastore.dialog.ProgressDialog
import com.taetae98.datastore.dto.InformationBody
import com.taetae98.datastore.dto.LoginBody
import com.taetae98.datastore.dto.SmartGachonMessage
import com.taetae98.datastore.singleton.AccountRepository
import com.taetae98.datastore.singleton.LoginRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    @Inject
    lateinit var api: SmartGachonAPI

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var accountRepository: AccountRepository

    private fun onLogin(id: String, password: String, isAutoLoginChecked: Boolean) {
        val dialog = ProgressDialog().also {
            it.show(parentFragmentManager, null)
        }

        api.login(LoginBody(id, password)).enqueue(object : Callback<SmartGachonMessage> {
            override fun onResponse(call: Call<SmartGachonMessage>, response: Response<SmartGachonMessage>) {
                response.body()?.let { message ->
                    when(message.code) {
                        "0" -> {
                            if (isAutoLoginChecked) {
                                loginRepository.id = id
                                loginRepository.password = password
                            } else {
                                loginRepository.id = ""
                                loginRepository.password = ""
                            }
                            onInformation(id)
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToQRFragment())
                        }
                        "9999" -> {
                            Toast.makeText(requireContext(), R.string.invalid_account, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(requireContext(), "Server Error Contact Developer", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                dialog.dismiss()
            }

            override fun onFailure(call: Call<SmartGachonMessage>, t: Throwable) {
                Toast.makeText(requireContext(), "Server Error Contact Developer", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })
    }

    private fun onInformation(id: String) {
        runBlocking(Dispatchers.IO) {
            api.information(InformationBody(id)).execute().body()?.let { message ->
                val information = message.information.first()
                accountRepository.set(information)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (loginRepository.id.isNotBlank() && loginRepository.password.isNotBlank()) {
            onLogin(loginRepository.id, loginRepository.password, true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateOnLogin()
        return view
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateOnLogin() {
        binding.setOnLogin {
            val id = binding.idTextInputLayout.editText!!.text.toString()
            val password = binding.passwordTextInputLayout.editText!!.text.toString()
            val isAutoLoginChecked = binding.autoLoginCheckBox.isChecked

            onLogin(id, password, isAutoLoginChecked)
        }
    }
}