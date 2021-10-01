package com.taetae98.glidecustomtarget

import android.app.SearchManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.taetae98.glidecustomtarget.databinding.ActivityMainBinding
import org.jsoup.Jsoup
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    private val imageUrlAdapter by lazy { ImageUrlAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        with(binding.recyclerView) {
            adapter = imageUrlAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(getSystemService(SearchManager::class.java).getSearchableInfo(componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    thread {
                        Jsoup.connect(query).get()
                            .select("img[src]")
                            .eachAttr("src")
                            .filter { imageUrl ->
                                imageUrl.startsWith("http")
                            }
                            .also {
                                runOnUiThread {
                                    imageUrlAdapter.submitList(it)
                                }
                            }
                    }

                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    return true
                }
            })
        }

        return true
    }
}