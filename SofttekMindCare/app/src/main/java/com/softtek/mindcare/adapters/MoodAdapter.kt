package com.softtek.mindcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softtek.mindcare.databinding.ItemMoodEntryBinding
import com.softtek.mindcare.models.MoodEntry
import java.text.SimpleDateFormat
import java.util.*

class MoodAdapter : ListAdapter<MoodEntry, MoodAdapter.MoodViewHolder>(MoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val binding = ItemMoodEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MoodViewHolder(private val binding: ItemMoodEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: MoodEntry) {
            with(binding) {
                moodType.text = entry.moodType
                intensityProgress.progress = entry.intensity
                intensityText.text = "${entry.intensity}/5"

                entry.note?.let {
                    moodNote.text = it
                    moodNote.visibility = View.VISIBLE
                } ?: run {
                    moodNote.visibility = View.GONE
                }

                val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                timestamp.text = dateFormat.format(Date(entry.timestamp))
            }
        }
    }

    private class MoodDiffCallback : DiffUtil.ItemCallback<MoodEntry>() {
        override fun areItemsTheSame(oldItem: MoodEntry, newItem: MoodEntry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoodEntry, newItem: MoodEntry): Boolean {
            return oldItem == newItem
        }
    }
}