package com.softtek.mindcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

    inner class SupportViewHolder(
        private val binding: ItemSupportResourceBinding,
        private val onResourceClicked: (SupportResource) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(resource: SupportResource) {
            with(binding) {
                title.text = resource.title
                description.text = resource.description

                when (resource.type) {
                    "PHONE" -> icon.setImageResource(R.drawable.ic_phone)
                    "EMAIL" -> icon.setImageResource(R.drawable.ic_email)
                    "URL" -> icon.setImageResource(R.drawable.ic_web)
                    else -> icon.setImageResource(R.drawable.ic_support)
                }

                favoriteIcon.setImageResource(
                    if (resource.isFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_border
                )

                favoriteIcon.setOnClickListener {
                    onResourceClicked(resource.copy(isFavorite = !resource.isFavorite))
                }

                root.setOnClickListener {
                    onResourceClicked(resource)
                }
            }
        }
    }

    private class SupportDiffCallback : DiffUtil.ItemCallback<SupportResource>() {
        override fun areItemsTheSame(oldItem: SupportResource, newItem: SupportResource): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SupportResource, newItem: SupportResource): Boolean {
            return oldItem == newItem
        }
    }
}