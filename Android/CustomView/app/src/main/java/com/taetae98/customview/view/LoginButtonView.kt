package com.taetae98.customview.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.google.android.material.card.MaterialCardView
import com.taetae98.customview.R
import com.taetae98.customview.databinding.ViewLoginButtonBinding
import com.taetae98.customview.utility.DataBinding


class LoginButtonView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : MaterialCardView(context, attrs, defStyleAttr), DataBinding<ViewLoginButtonBinding> {
    override val binding: ViewLoginButtonBinding by lazy { DataBinding.get(this, R.layout.view_login_button) }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LoginButtonView, defStyleAttr, defStyleRes).apply {
            if (isInEditMode) {
                inflate(context, R.layout.view_login_button, this@LoginButtonView)
                findViewById<TextView>(R.id.text_view).apply {
                    text = getString(R.styleable.LoginButtonView_text)
                    setTextColor(getColor(R.styleable.LoginButtonView_textColor, Color.parseColor("#000000")))
                }
                findViewById<ImageView>(R.id.icon_image_view).apply {
                    setImageDrawable(getDrawable(R.styleable.LoginButtonView_icon))
                }
            } else {
                with(binding) {
                    icon = getDrawable(R.styleable.LoginButtonView_icon)
                    text = getString(R.styleable.LoginButtonView_text)
                    textColor = getColor(R.styleable.LoginButtonView_textColor, Color.parseColor("#000000"))
                }
            }

            recycle()
        }
    }
}