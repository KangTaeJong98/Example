package com.taetae98.lifecycle

import android.os.SystemClock
import androidx.activity.viewModels
import com.taetae98.lifecycle.adapter.RecordAdapter
import com.taetae98.lifecycle.base.BaseActivity
import com.taetae98.lifecycle.data.Record
import com.taetae98.lifecycle.databinding.ActivityMainBinding
import com.taetae98.lifecycle.model.ChronometerViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
//    생성자가 필요한 ViewModel 사용하는 코드. (ViewModelProvider.Factory 사용하면 된다.)
//    private val model by viewModels<ChronometerViewModel> { ChronometerViewModelFactory(3000L) }

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
        super.init()
        initChronometer()
        initViewModel()
        initStartButton()
        initStopButton()
        initResumeButton()
        initResetButton()
        initRecordButton()
        initRecyclerView()
    }

    private fun initViewModel() {
        with(model) {
            state.observe(this@MainActivity) {
                binding.state = it
                when(it ?: ChronometerViewModel.State.RESET) {
                    ChronometerViewModel.State.RUN -> {
                        binding.chronometer.start()
                    }
                    ChronometerViewModel.State.RESET -> {
                        binding.chronometer.stop()
                    }
                    ChronometerViewModel.State.STOP -> {
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