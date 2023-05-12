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
                        val dateEnd = tvDate.text.toString()
                        val timeEnd = tvTime.text.toString()
                        if (noteText.isNotEmpty()) {
                            viewModel.addNote(noteText, dateEnd, timeEnd)
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
        val calendar = Calendar.getInstance()
        val iYear = calendar[Calendar.YEAR]
        val iMonth = calendar[Calendar.MONTH]
        val iDay = calendar[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val sDate = "$dayOfMonth.${month+1}.$year"
                binding.tvDate.text = sDate
            },
            iYear, iMonth, iDay
        )
        datePickerDialog.show()
    }

    private fun showTime() {
        val calendar = Calendar.getInstance()
        val iHour = calendar[Calendar.HOUR]
        val iMinute = calendar[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                binding.tvTime.text = "$hourOfDay:$minute"
//                    String.format("%d:%d", hourOfDay, minute)
            }, iHour, iMinute, true)
        timePickerDialog.show()
    }
}