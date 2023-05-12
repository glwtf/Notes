package com.example.notes.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notes.R
import com.example.notes.databinding.ItemBinding
import com.example.notes.domain.Note
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class NotesListAdapter(
    private val options: FirebaseRecyclerOptions<Note>,
) : FirebaseRecyclerAdapter<Note, ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item, parent, false)
        val binding = ItemBinding.bind(view)
        return NoteItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Note) {
        if (options.snapshots[position].text != null)
                (holder as NoteItemViewHolder).bind(model)
    }

    fun remove(position: Int) {
        notifyItemRemoved(position)
    }

    class NoteItemViewHolder(private val binding: ItemBinding) : ViewHolder(binding.root) {

        fun bind(item: Note) {
            binding.etText.setText(item.text)
        }
    }
}