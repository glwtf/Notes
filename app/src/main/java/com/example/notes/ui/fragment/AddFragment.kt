package com.example.notes.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.databinding.FragmentAddBinding
import com.example.notes.ui.viewmodel.AddFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding: FragmentAddBinding
        get() = _binding ?: throw RuntimeException("FragmentAddBinding is null")

    private val viewModel: AddFragmentViewModel by viewModels()
    private var planTimeMillis: Long? = null
    private val calendar: Calendar by lazy {
        Calendar.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        with(binding) {
            val onClickListener = View.OnClickListener {
                when (it.id) {
                    sendFirebase.id -> {
                        val noteText = etTextAdd.text.toString()
                        if (noteText.isNotEmpty()) {
                            viewModel.addNote(noteText,planTimeMillis)
                            findNavController().navigateUp()
                        }
                    }
                    imageDate.id, tvDate.id -> {
                        if (switchDate.isChecked) {
                            showDate()
                        }
                    }
                    imageTime.id, tvTime.id -> {
                        if (switchTime.isChecked) {
                            showTime()
                        }
                    }

                }
            }
            sendFirebase.setOnClickListener(onClickListener)
            imageDate.setOnClickListener(onClickListener)
            tvDate.setOnClickListener(onClickListener)
            imageTime.setOnClickListener(onClickListener)
            tvTime.setOnClickListener(onClickListener)
        }
    }

    private fun showDate() {
        val localCalendar = Calendar.getInstance()
        val iYear = localCalendar[Calendar.YEAR]
        val iMonth = localCalendar[Calendar.MONTH]
        val iDay = localCalendar[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                binding.tvDate.text = String.format("%02d/%02d/%04d",dayOfMonth,month+1, year)
                calendar.set(year, month, dayOfMonth)
                planTimeMillis = calendar.timeInMillis
            },
            iYear, iMonth, iDay
        )
        datePickerDialog.show()
    }

    private fun showTime() {
        val localCalendar = Calendar.getInstance()
        val iHour = localCalendar[Calendar.HOUR]
        val iMinute = localCalendar[Calendar.MINUTE]

        val iYear = calendar[Calendar.YEAR]
        val iMonth = calendar[Calendar.MONTH]
        val iDay = calendar[Calendar.DAY_OF_MONTH]
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                binding.tvTime.text = String.format("%02d:%02d",hourOfDay,minute)
                calendar.set(iYear, iMonth, iDay, hourOfDay, minute)
                planTimeMillis = calendar.timeInMillis
            }, iHour, iMinute, true)
        timePickerDialog.show()
    }
}