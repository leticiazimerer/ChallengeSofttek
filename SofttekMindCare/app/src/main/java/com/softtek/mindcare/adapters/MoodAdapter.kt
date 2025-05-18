package com.softtek.mindcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
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
            binding.moodType.text = entry.moodType
            binding.intensityProgress.progress = entry.intensity
            binding.intensityText.text = "${entry.intensity}/5"
            binding.timestamp.text = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
                .format(Date(entry.timestamp))

            entry.note?.let {
                binding.moodNote.text = it
                binding.moodNote.visibility = View.VISIBLE
            } ?: run {
                binding.moodNote.visibility = View.GONE
            }
        }
    }

    class MoodDiffCallback : DiffUtil.ItemCallback<MoodEntry>() {
        override fun areItemsTheSame(oldItem: MoodEntry, newItem: MoodEntry) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MoodEntry, newItem: MoodEntry) =
            oldItem == newItem
    }
}