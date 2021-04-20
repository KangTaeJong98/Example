package com.taetae98.flow

import androidx.databinding.ViewDataBinding

interface DataBinding<VB: ViewDataBinding> {
    val binding: VB
}