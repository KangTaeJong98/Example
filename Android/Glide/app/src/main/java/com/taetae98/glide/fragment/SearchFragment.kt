package com.taetae98.glide.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import com.taetae98.glide.R
import com.taetae98.glide.adapter.ImageUriAdapter
import com.taetae98.glide.base.BaseFragment
import com.taetae98.glide.databinding.FragmentSearchBinding
import com.taetae98.glide.utility.DataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class SearchFragment : BaseFragment(), DataBinding<FragmentSearchBinding> {
    override val binding by lazy { DataBinding.get<FragmentSearchBinding>(this, R.layout.fragment_search) }

    private val imageUriAdapter by lazy { ImageUriAdapter() }

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onCreateViewDataBinding()
        onCreateSupportActionBar()
        onCreateImageRecyclerView()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_fragment_menu, menu)

        val view = menu.findItem(R.id.search).actionView as SearchView
        view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    val arrayList = ArrayList<String>()
                    withContext(Dispatchers.IO) {
                        Jsoup.connect(query).get().select("img[src]").forEach {
                            val uri = it.attr("src")
                            if (uri.contains("http")) {
                                arrayList.add(uri)
                            }
                        }
                    }

                    withContext(Dispatchers.Main) {
                        imageUriAdapter.submitList(arrayList)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateImageRecyclerView() {
        with(binding.recyclerView) {
            adapter = imageUriAdapter
        }
    }
}