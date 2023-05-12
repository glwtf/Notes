package com.example.notes.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.FragmentMainBinding
import com.example.notes.domain.Note
import com.example.notes.ui.recyclerview.NotesListAdapter
import com.example.notes.ui.recyclerview.SwipeToDeleteCallback
import com.example.notes.ui.viewmodel.MainFragmentViewModel
import com.firebase.ui.database.FirebaseRecyclerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.lang.RuntimeException

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding : FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private val viewModel: MainFragmentViewModel by viewModels()

    private val firebaseAdapter by lazy {
        val options = FirebaseRecyclerOptions.Builder<Note>()
            .setQuery(viewModel.getFirebaseDbRef(), Note::class.java)
            .build()
        NotesListAdapter(options)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setOnClickListener()
    }

    private fun setRecyclerView() {
        binding.rvNotesList.adapter = firebaseAdapter
        setSwipeToDelete(binding.rvNotesList)
    }

    private fun setOnClickListener() {
        binding.addButton.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToAddFragment()
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        firebaseAdapter.notifyDataSetChanged()
        firebaseAdapter.startListening()
    }

    private fun setSwipeToDelete(rvList: RecyclerView) {
        ItemTouchHelper(object : SwipeToDeleteCallback(requireActivity().applicationContext) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val item = firebaseAdapter.getItem(viewHolder.absoluteAdapterPosition)
                    viewModel.deleteNote(item)
                    firebaseAdapter.remove(viewHolder.absoluteAdapterPosition)
                }
        }).attachToRecyclerView(rvList)
    }

    override fun onPause() {
        firebaseAdapter.stopListening()
        super.onPause()
    }
}