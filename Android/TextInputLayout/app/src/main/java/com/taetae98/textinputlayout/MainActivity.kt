package com.taetae98.textinputlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.taetae98.textinputlayout.base.BaseActivity
import com.taetae98.textinputlayout.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun init() {
        super.init()
        initAutoCompleteTextView()
    }

    private fun initAutoCompleteTextView() {
        val array = arrayOf("Apple", "Banana", "Watermelon")
        (binding.autoComplete.editText as? AutoCompleteTextView)?.apply {
            setAdapter(ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, array))
        }
    }
}