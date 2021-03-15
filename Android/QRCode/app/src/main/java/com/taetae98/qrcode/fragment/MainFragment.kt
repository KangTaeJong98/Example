package com.taetae98.qrcode.fragment

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.taetae98.qrcode.R
import com.taetae98.qrcode.base.BaseFragment
import com.taetae98.qrcode.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    init {
        setHasOptionsMenu(true)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData("QR CODE", "Hello World")?.observe(viewLifecycleOwner) {
            binding.qrCode = it
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.qr_code -> {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(R.string.qr_code)

                    val items = arrayOf(getString(R.string.generate), getString(R.string.scan))
                    setItems(items) { _, position ->
                        when(items[position]) {
                            getString(R.string.generate) -> {
                                findNavController().navigate(MainFragmentDirections.actionMainFragmentToGenerateDialog())
                            }
                            getString(R.string.scan) -> {
                                findNavController().navigate(MainFragmentDirections.actionMainFragmentToScanDialog())
                            }
                        }
                    }
                }.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun init() {
        super.init()
        initSupportActionBar()
        initOnClick()
    }

    private fun initSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initOnClick() {
        binding.setOnClick {
            (requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).apply {
                setPrimaryClip(ClipData.newPlainText("QRCode", binding.qrCode))
            }

            Toast.makeText(requireContext(), "Copy : ${binding.qrCode}", Toast.LENGTH_SHORT).show()
        }
    }
}