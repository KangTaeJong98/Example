package com.taetae98.dialog.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.widget.Toast
import com.taetae98.dialog.R
import com.taetae98.dialog.base.BaseFragment
import com.taetae98.dialog.databinding.FragmentMainBinding
import com.taetae98.dialog.databinding.LayoutCustomAlertBinding
import com.taetae98.dialog.dialog.CustomDialog
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val items by lazy { arrayOf("Apple", "Banana", "Watermelon") }
    private val selectedBooleanArray by lazy { BooleanArray(items.size) { false } }

    override fun init() {
        initSupportActionBar()
        initOnDatePicker()
        initOnTimePicker()
        initOnAlert()
        initOnSelectAlert()
        initOnRadioAlert()
        initOnCheckboxAlert()
        initOnCustomAlert()
        initOnCustomDialog()
    }

    private fun initSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initOnDatePicker() {
        binding.setOnDatePicker {
            val calendar = GregorianCalendar()
            DatePickerDialog(requireContext(), { dialog, year, month, dayOfMonth ->
                binding.result = """
                    Dialog : ${dialog.id}
                    Event  : ${SimpleDateFormat.getDateInstance().format(GregorianCalendar(year, month, dayOfMonth).timeInMillis)}
                """.trimIndent()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun initOnTimePicker() {
        val calendar = GregorianCalendar()
        binding.setOnTimePicker {
            TimePickerDialog(requireContext(), { dialog, hourOfDay, minute ->
                binding.result = """
                    Dialog : ${dialog.id}
                    Event  : ${SimpleDateFormat.getTimeInstance().format(GregorianCalendar().apply { 
                        set(Calendar.HOUR_OF_DAY, hourOfDay)
                        set(Calendar.MINUTE, minute)
                        set(Calendar.SECOND, 0)
                    }.timeInMillis)}
                """.trimIndent()
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()
        }
    }

    private fun initOnAlert() {
        binding.setOnAlert {
            AlertDialog.Builder(requireContext()).apply {
                setIcon(R.drawable.ic_info)
                setTitle("This is Title")
                setMessage("This is Message")
                setPositiveButton("Positive") { _, which ->
                    binding.result = "Positive $which"
                }
                setNegativeButton("Negative") { _, which ->
                    binding.result = "Negative $which"
                }
                setNeutralButton("Neutral") { _, which ->
                    binding.result = "Neutral $which"
                }
            }.show()
        }
    }

    private fun initOnSelectAlert() {
        binding.setOnSelectAlert {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Select Item")
                setItems(items) { _, position ->
                    selectedBooleanArray[position] = true
                    binding.result = "Select ${items[position]}"
                }
            }.show()
        }
    }

    private fun initOnRadioAlert() {
        binding.setOnRadioAlert {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Radio Alert")
                var selected = selectedBooleanArray.indexOfFirst { it }
                setSingleChoiceItems(items, selected) { _, position ->
                    selected = position
                    Toast.makeText(requireContext(), items[position], Toast.LENGTH_SHORT).show()
                }
                setPositiveButton("Select") { _, _ ->
                    selectedBooleanArray.fill(false)
                    selectedBooleanArray[selected] = true
                    binding.result = "Select ${items[selected]}"
                }
            }.show()
        }
    }

    private fun initOnCheckboxAlert() {
        binding.setOnCheckBoxAlert {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Checkbox Alert")
                val selected = BooleanArray(items.size) { selectedBooleanArray[it] }
                setMultiChoiceItems(items, selected) { _, position, boolean ->
                    selected[position] = boolean
                    Toast.makeText(requireContext(), "${items[position]} $boolean", Toast.LENGTH_SHORT).show()
                }
                setPositiveButton("Select") { _, _ ->
                    selected.forEachIndexed { position, boolean ->
                        selectedBooleanArray[position] = boolean
                    }

                    binding.result = "Select\n"
                    selected.forEachIndexed { index, boolean ->
                        if (boolean) {
                            binding.result += "${items[index]}\n"
                        }
                    }
                }
            }.show()
        }
    }

    private fun initOnCustomAlert() {
        binding.setOnCustomAlert {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Custom Alert")
                setView(LayoutCustomAlertBinding.inflate(LayoutInflater.from(context)).root)
            }.show()
        }
    }

    private fun initOnCustomDialog() {
        binding.setOnCustomDialog {
//            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCustomDialog())
            CustomDialog().show(parentFragmentManager, "")
        }
    }
}