package com.taetae98.flow

import android.os.Bundle
import androidx.activity.viewModels
import com.taetae98.flow.databinding.ActivityMainBinding
import com.taetae98.flow.databinding.BindingActivity
import com.taetae98.flow.viewmodel.FlowViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel by viewModels<FlowViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateOnCollect()
        onCreateOnFirst()
        onCreateOnCollectLatest()
        onCreateOnMap()
        onCreateOnException()
        onCreateOnChannelFlow()
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.viewModel = viewModel
    }

    private fun onCreateOnCollect() {
        binding.setOnCollect {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.clearLog()
                viewModel.doLog("Collect Start")
                viewModel.data.collect {
                    viewModel.doLog("Collect $it")
                }
                viewModel.doLog("Collect End")
            }
        }
    }

    private fun onCreateOnFirst() {
        binding.setOnFirst {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.clearLog()
                viewModel.doLog("First Start")
                viewModel.data.first {
                    viewModel.doLog("First $it")
                    true

                    // False면 다시 받아온다.
                    // NoSuchElement 발생 가능성 있음
                }
                viewModel.doLog("First End")
            }
        }
    }

    private fun onCreateOnCollectLatest() {
        binding.setOnCollectLatest {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.clearLog()
                viewModel.doLog("Collect Latest Start")
                viewModel.data.collectLatest {
                    viewModel.doLog("Collect Latest : $it")
                }
                viewModel.doLog("Collect Latest End")
            }
        }
    }

    private fun onCreateOnMap() {
        binding.setOnMap {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.clearLog()
                viewModel.doLog("Map Start")
                viewModel.data.map {
                    it + 10
                }.filter {
                    it > 0
                }.onEach {
                    viewModel.doLog("Map -> onEach : $it")
                }.collect {
                    viewModel.doLog("Map -> onEach -> Collect : $it")
                }
                viewModel.doLog("Map End")
            }
        }
    }

    private fun onCreateOnException() {
        binding.setOnException {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.clearLog()
                viewModel.doLog("Exception Start")
                viewModel.exceptionData.catch {
                    viewModel.doLog(it.message)
                    emit(10)
                }.collect {
                    viewModel.doLog("Exception Collect : $it")
                }

                viewModel.doLog("Exception End")
            }
        }
    }

    private fun onCreateOnChannelFlow() {
        binding.setOnFlowOn {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.clearLog()
                viewModel.doLog("FlowOn Start")
                viewModel.flowOn.collect {
                    viewModel.doLog("FlowOn :  $it")
                }
                viewModel.doLog("FlowOn End")
            }
        }
    }
}