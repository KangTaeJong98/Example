package com.taetae98.customview.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.google.android.material.card.MaterialCardView
import com.taetae98.customview.R
import com.taetae98.customview.databinding.ViewLoginButtonBinding
import com.taetae98.customview.utility.DataBinding

class LoginButtonView : MaterialCardView, DataBinding<ViewLoginButtonBinding> {
    override val binding: ViewLoginButtonBinding = DataBinding.get(this, R.layout.view_login_button)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginButtonView)
        setAttr(typedArray)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginButtonView, defStyleAttr, 0)
        setAttr(typedArray)
    }

    private fun setAttr(typedArray: TypedArray) {
        with(binding) {
            text = typedArray.getString(R.styleable.LoginButtonView_text)
            textColor = typedArray.getColor(R.styleable.LoginButtonView_textColor, Color.parseColor("#000000"))
            icon = typedArray.getDrawable(R.styleable.LoginButtonView_icon)
        }
    }
}