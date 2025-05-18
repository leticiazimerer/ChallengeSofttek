package com.softtek.mindcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.softtek.mindcare.databinding.ItemSupportResourceBinding
import com.softtek.mindcare.models.SupportResource

class SupportResourceAdapter(
    private val onResourceClicked: (SupportResource) -> Unit
) : ListAdapter<SupportResource, SupportResourceAdapter.SupportViewHolder>(SupportDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupportViewHolder {
        val binding = ItemSupportResourceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SupportViewHolder(binding, onResourceClicked)
    }

    override fun onBindViewHolder(holder: SupportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SupportViewHolder(
        private val binding: ItemSupportResourceBinding,
        private val onResourceClicked: (SupportResource) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(resource: SupportResource) {
            binding.title.text = resource.title
            binding.description.text = resource.description

            binding.root.setOnClickListener {
                onResourceClicked(resource)
            }
        }
    }

    class SupportDiffCallback : DiffUtil.ItemCallback<SupportResource>() {
        override fun areItemsTheSame(oldItem: SupportResource, newItem: SupportResource) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SupportResource, newItem: SupportResource) =
            oldItem == newItem
    }
}