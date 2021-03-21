package com.taetae98.lifecycle.fragment

import android.os.SystemClock
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.taetae98.lifecycle.R
import com.taetae98.lifecycle.adapter.RecordAdapter
import com.taetae98.lifecycle.base.BaseFragment
import com.taetae98.lifecycle.data.Record
import com.taetae98.lifecycle.databinding.FragmentChronometerBinding
import com.taetae98.lifecycle.model.ChronometerViewModel

class ChronometerFragment : BaseFragment<FragmentChronometerBinding>(R.layout.fragment_chronometer) {
    //    생성자가 필요한 ViewModel을 사용하는 코드. (ViewModelProvider.Factory를 사용하면 된다.)
//    private val model by lazy { ViewModelProvider(this, ChronometerViewModelFactory(3000L)).get(ChronometerViewModel::class.java) }
//    private val model by viewModels<ChronometerViewModel> { ChronometerViewModelFactory(3000L) }


    //    생성자가 필요없는 ViewModel을 사용하는 코드
//    private val model by lazy { ViewModelProvider(this).get(ChronometerViewModel::class.java) }
    private val model by viewModels<ChronometerViewModel>()
    private val recordAdapter by lazy { RecordAdapter() }

    private fun onResetChronometer() {
        model.state.value = ChronometerViewModel.State.RESET
        model.sumOfTickTime = 0L
        binding.chronometer.base = SystemClock.elapsedRealtime()

        val count = model.recordList.size
        model.recordList.clear()
        recordAdapter.notifyItemRangeRemoved(0, count)
    }

    private fun onStartChronometer() {
        model.state.value = ChronometerViewModel.State.START

        val tickTime = SystemClock.elapsedRealtime()
        model.lastTickTime = tickTime
        binding.chronometer.base = SystemClock.elapsedRealtime() - model.sumOfTickTime

        onRunChronometer()
    }

    private fun onRunChronometer() {
        model.state.value = ChronometerViewModel.State.RUN
    }

    private fun onResumeChronometer() {
        model.state.value = ChronometerViewModel.State.RESUME

        val tickTime = SystemClock.elapsedRealtime()
        model.lastTickTime = tickTime
        binding.chronometer.base = SystemClock.elapsedRealtime() - model.sumOfTickTime

        onRunChronometer()
    }

    private fun onStopChronometer() {
        model.state.value = ChronometerViewModel.State.STOP
        binding.chronometer.base = SystemClock.elapsedRealtime() - model.sumOfTickTime
    }

    override fun init() {
        initSupportActionbar()
        initChronometer()
        initViewModel()
        initStartButton()
        initStopButton()
        initResumeButton()
        initResetButton()
        initRecordButton()
        initRecyclerView()
    }

    private fun initSupportActionbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initViewModel() {
        with(model) {
            state.observe(this@ChronometerFragment) {
                binding.state = it
                when(it ?: com.taetae98.lifecycle.model.ChronometerViewModel.State.RESET) {
                    com.taetae98.lifecycle.model.ChronometerViewModel.State.RUN -> {
                        binding.chronometer.start()
                    }
                    com.taetae98.lifecycle.model.ChronometerViewModel.State.RESET -> {
                        binding.chronometer.stop()
                    }
                    com.taetae98.lifecycle.model.ChronometerViewModel.State.STOP -> {
                        binding.chronometer.stop()
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun initChronometer() {
        with(binding.chronometer) {
            base = SystemClock.elapsedRealtime() - model.sumOfTickTime
            setOnChronometerTickListener {
                val tickTime = SystemClock.elapsedRealtime()
                model.sumOfTickTime += tickTime - model.lastTickTime
                model.lastTickTime = tickTime
            }
        }
    }

    private fun initResetButton() {
        binding.setOnReset {
            onResetChronometer()
        }
    }

    private fun initStartButton() {
        binding.setOnStart {
            onStartChronometer()
        }
    }

    private fun initStopButton() {
        binding.setOnStop {
            onStopChronometer()
        }
    }

    private fun initResumeButton() {
        binding.setOnResume {
            onResumeChronometer()
        }
    }

    private fun initRecordButton() {
        binding.setOnRecord {
            with(model) {
                recordList.add(Record(recordList.size.toLong(), sumOfTickTime))
                recordAdapter.notifyItemInserted(recordList.size - 1)
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = recordAdapter
            recordAdapter.submitList(model.recordList)
        }
    }
}