package com.taetae98.textinputlayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.widget.addTextChangedListener
import com.taetae98.textinputlayout.base.BaseActivity
import com.taetae98.textinputlayout.databinding.ActivityJoinBinding
import java.util.regex.Pattern

class JoinActivity : BaseActivity<ActivityJoinBinding>(R.layout.activity_join) {
    override fun init() {
        super.init()
        initIdTextInputLayout()
        initNameTextInputLayout()
        initPasswordTextInputLayout()
        initPasswordRepeatTextInputLayout()
        initOnJoinButton()
    }

    private fun initIdTextInputLayout() {
        with(binding.id) {
            editText?.addTextChangedListener {
                it?.let {
                    error = if(android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                        null
                    } else {
                        getString(R.string.write_your_email)
                    }
                }
            }
        }
    }

    private fun initNameTextInputLayout() {
        with(binding.name) {
            editText?.addTextChangedListener {
                it?.let {
                    error = if (Pattern.matches("[\\w]+", it)) {
                        null
                    } else {
                        getString(R.string.write_only_text)
                    }
                }
            }
        }
    }

    private fun initPasswordTextInputLayout() {
        with(binding.password) {
            editText?.addTextChangedListener {
                it?.let {
                    error = if(Pattern.matches("([\\w\\d]*[~!@#$%^&*()_+=:;,./<>?{}]+[\\w\\d]*)", it)) {
                        null
                    } else {
                        getString(R.string.include_one_or_more_special_characters)
                    }

                    if (binding.passwordRepeat.editText?.text?.trim()?.isNotEmpty() == true) {
                        binding.passwordRepeat.error = if (editText?.text?.toString() == binding.passwordRepeat.editText?.text?.toString()) {
                            null
                        } else {
                            getString(R.string.write_your_password)
                        }
                    }
                }
            }
        }
    }

    private fun initPasswordRepeatTextInputLayout() {
        with(binding.passwordRepeat) {
            editText?.addTextChangedListener {
                error = if (it?.toString() == binding.password.editText?.text?.toString()) {
                    null
                } else {
                    getString(R.string.write_your_password)
                }
            }
        }
    }

    private fun initOnJoinButton() {
        binding.setOnJoinButton {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}