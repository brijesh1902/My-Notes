package com.brizzs.mynotes.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brizzs.mynotes.databinding.ViewNotesBinding
import com.brizzs.mynotes.models.Notes
import com.brizzs.mynotes.ui.AddNotesActivity
import com.brizzs.mynotes.utils.CURRENT_NOTE
import com.brizzs.mynotes.utils.DATE_TIME_FORMAT
import com.brizzs.mynotes.utils.getMarkWonBuilder
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import org.commonmark.node.SoftLineBreak
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class NotesAdapter : ListAdapter<Notes, NotesAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ViewNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(private val binding : ViewNotesBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(notes: Notes) {

            binding.title.text = notes.title
            getMarkWonBuilder(itemView.context).setMarkdown(binding.notes, notes.notes.toString())

            val format = SimpleDateFormat(DATE_TIME_FORMAT)

            binding.time.text = format.format(notes.date?.toLongOrNull())

            binding.notesCard.setCardBackgroundColor(itemView.resources.getColor(notes.randomColor, null))

            binding.notesCard.setOnClickListener {
                itemView.context.startActivity(Intent(itemView.context, AddNotesActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra(CURRENT_NOTE, notes))
            }
            binding.notes.setOnClickListener {
                itemView.context.startActivity(Intent(itemView.context, AddNotesActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra(CURRENT_NOTE, notes))
            }
        }

    }

}

class DiffCallback : DiffUtil.ItemCallback<Notes>() {
    override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem.id == newItem.id || oldItem.title == newItem.title || oldItem.notes == newItem.notes
    }

    override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem == newItem
    }
}