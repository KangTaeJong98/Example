package com.taetae98.hilt

import com.taetae98.hilt.base.BaseActivity
import com.taetae98.hilt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main)