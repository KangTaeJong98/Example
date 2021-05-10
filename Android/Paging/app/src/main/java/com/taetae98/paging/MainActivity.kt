package com.taetae98.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val viewModel by lazy { ViewModelProvider(this).get(PagingViewModel::class.java) }
    val pagingAdapter = PagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(findViewById<RecyclerView>(R.id.recycler_view)) {
            adapter = pagingAdapter
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }
}