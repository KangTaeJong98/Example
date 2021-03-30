package com.taetae98.datastore.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.taetae98.datastore.R
import com.taetae98.datastore.base.BaseFragment
import com.taetae98.datastore.databinding.FragmentQrBinding
import com.taetae98.datastore.singleton.AccountRepository
import com.taetae98.datastore.singleton.LoginRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class QRFragment : BaseFragment<FragmentQrBinding>(R.layout.fragment_qr) {
    init {
        setHasOptionsMenu(true)
    }

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var accountRepository: AccountRepository

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_qr_option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> {
                loginRepository.id = ""
                loginRepository.password = ""
                findNavController().navigateUp()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateOnRefresh()

        refresh()
        return view
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateOnRefresh() {
        binding.setOnRefresh {
            refresh()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun refresh() {
        CoroutineScope(Dispatchers.IO).launch {
            val studentId = accountRepository.studentId.first()
            withContext(Dispatchers.Main) {
                binding.qrCode = "m$studentId${SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())}"
                binding.lastUpdated = "Last Updated : ${SimpleDateFormat.getTimeInstance().format(System.currentTimeMillis())}\n${binding.qrCode}"
            }
        }
    }
}