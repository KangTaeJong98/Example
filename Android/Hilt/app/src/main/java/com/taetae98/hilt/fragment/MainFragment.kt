package com.taetae98.hilt.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.taetae98.hilt.R
import com.taetae98.hilt.adapter.SummonerInformationAdapter
import com.taetae98.hilt.base.BaseFragment
import com.taetae98.hilt.data.SummonerEntity
import com.taetae98.hilt.database.SummonerEntityRepository
import com.taetae98.hilt.databinding.FragmentMainBinding
import com.taetae98.hilt.utility.GridSpacingItemDecoration
import com.taetae98.hilt.viewmodel.SummonerEntityViewModel
import com.taetae98.hilt.qualifier.DataSource
import com.taetae98.hilt.qualifier.LocalDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    @Inject
    lateinit var summonerEntityRepository: SummonerEntityRepository

    @Inject
    lateinit var summonerInformationAdapter: SummonerInformationAdapter

    @Inject
    @LocalDatabase
    lateinit var database: DataSource

    private val summonerEntityViewModel by viewModels<SummonerEntityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        summonerEntityViewModel.summonerInformationLiveData.observe(viewLifecycleOwner) {
            summonerInformationAdapter.submitList(it)
        }
    }

    override fun init() {
        initSupportActionBar()
        initSummonerWithEntityRecyclerView()
        initOnAdd()
    }

    private fun initSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initSummonerWithEntityRecyclerView() {
        with(binding.summonerWithEntityRecyclerView) {
            adapter = summonerInformationAdapter
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (viewHolder is SummonerInformationAdapter.SummonerInformationHolder) {
                        summonerEntityRepository.deleteSummonerEntity(viewHolder.element.entity)
                    }
                }
            }).attachToRecyclerView(this)
            addItemDecoration(GridSpacingItemDecoration(10))
        }
    }

    private fun initOnAdd() {
        binding.setOnAdd {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSummonerEntityEditDialog(
                SummonerEntity()
            ))
        }
    }
}