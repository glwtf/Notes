package com.example.notes.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val calendar = Calendar.getInstance()
        val iYear = calendar[Calendar.YEAR]
        val iMonth = calendar[Calendar.MONTH]
        val iDay = calendar[Calendar.DAY_OF_MONTH]
        with(binding) {
            val onClickListener = View.OnClickListener {
                when (it.id) {
                    sendFirebase.id -> {
                        val noteText = etTextAdd.text.toString()
                        if (noteText.isNotEmpty()) {
                            viewModel.addNote(noteText)
                        }
                    }
                    imageDate.id, tvDate.id -> {
                        if (switchDate.isChecked) {
                            val datePickerDialog = DatePickerDialog(
                                requireContext(),
                                { _, year, month, dayOfMonth ->
                                    val sDate = "$dayOfMonth.$month.$year"
                                    tvDate.text = sDate
                                },
                                iYear, iMonth, iDay
                            )
                            datePickerDialog.show()
                        }
                    }
                    imageTime.id, tvTime.id -> {

                    }

                }
            }
            sendFirebase.setOnClickListener(onClickListener)
            imageDate.setOnClickListener(onClickListener)
            tvDate.setOnClickListener(onClickListener)
        }
    }
}