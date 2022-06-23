package com.codex.hackernews.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codex.hackernews.AssesmentApp
import com.codex.hackernews.R
import com.codex.hackernews.data.model.Item
import com.codex.hackernews.databinding.ItemStoryBinding


class StoryAdapter(
    private val onItemClicked: (item: Item) -> Unit?
) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    private val listItem: ArrayList<Item> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(listItem[position])

        holder.itemView.setOnClickListener {
            onItemClicked(listItem[position])
        }
    }

    override fun getItemCount(): Int = listItem.size


    fun submitData(item:Item){
        listItem.add(item)
        notifyItemInserted(itemCount+1)
    }

    class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Item) {
            binding.storyTitle.text = data.title
            binding.valueJumlahKomentar.text = data.descendants.toString()
            binding.valueScore.text = data.score.toString()
            val drawable = if (data.isFavourite == true){
                ResourcesCompat.getDrawable(AssesmentApp.applicationContext().resources, R.drawable.ic_star_yellow, null)
            }else{
                ResourcesCompat.getDrawable(AssesmentApp.applicationContext().resources, R.drawable.ic_star_grey, null)
            }
            binding.isFavourite.setImageDrawable(drawable)
        }
    }
}