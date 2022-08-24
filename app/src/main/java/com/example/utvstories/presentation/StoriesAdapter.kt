package com.example.utvstories.presentation

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.utvstories.data.remote.models.StoryDto
import com.example.utvstories.databinding.StoryItemBinding

class StoriesAdapter(val onClick: (String) -> Unit) :
    ListAdapter<StoryDto, StoriesAdapter.StoryItemViewHolder>(StoryDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryItemViewHolder {
        val binding = StoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryItemViewHolder, position: Int) {
        val item = getItem(position)
        with (holder.binding) {
            Glide.with(previewImage.context).load(item.preview).into(previewImage)
            newsTitle.text = item.title
            date.text = item.postDate
            root.setOnClickListener {
                onClick(item.url)
            }
        }
    }

    inner class StoryItemViewHolder(val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

object StoryDiffUtilCallback : DiffUtil.ItemCallback<StoryDto>() {
    override fun areItemsTheSame(oldItem: StoryDto, newItem: StoryDto): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: StoryDto, newItem: StoryDto): Boolean =
        oldItem == newItem
}