package com.taetae98.span

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StyleRes
import com.taetae98.modules.library.binding.BindingActivity
import com.taetae98.span.databinding.ActivityMainBinding
import java.util.*

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        executeSpan()
        absoluteSizeSpan()
        relativeSizeSpan()
        clickSpan()
        urlSpan()
        bulletSpan()
    }

    private fun executeSpan() {
        with(binding.foregroundSpan) {
            val spannableString = SpannableString(text).apply {
                setSpan(ForegroundColorSpan(Color.BLUE), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(BackgroundColorSpan(Color.GRAY), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(UnderlineSpan(), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(StyleSpan(Typeface.ITALIC), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            setText(spannableString, TextView.BufferType.SPANNABLE)
        }
    }

    private fun absoluteSizeSpan() {
        with(binding.absoluteSizeSpan) {
            val spannableString = SpannableString(text).apply {
                setSpan(AbsoluteSizeSpan(30, true), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            setText(spannableString, TextView.BufferType.SPANNABLE)
        }
    }

    private fun relativeSizeSpan() {
        with(binding.relativeSizeSpan) {
            val spannableString = SpannableString(text).apply {
                setSpan(RelativeSizeSpan(1.5F), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            setText(spannableString, TextView.BufferType.SPANNABLE)
        }
    }

    private fun clickSpan() {
        with(binding.clickSpan) {
            val spannableString = SpannableString(text).apply {
                setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        Toast.makeText(this@MainActivity, if (widget is TextView) text else "클릭", Toast.LENGTH_SHORT).show()
                    }
                }, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            setText(spannableString, TextView.BufferType.SPANNABLE)
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun urlSpan() {
        with(binding.urlSpan) {
            val spannableString = SpannableString(text).apply {
                setSpan(URLSpan("https://rkdxowhd98.tistory.com/"), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            setText(spannableString, TextView.BufferType.SPANNABLE)
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun bulletSpan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            with(binding.bulletSpan) {
                val spannableString = SpannableString(text).apply {
                    // start가 0일 때 작동
                    setSpan(BulletSpan(25, Color.GREEN, 25), 0, 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }

                setText(spannableString, TextView.BufferType.SPANNABLE)
            }
        }
    }
}