package com.codex.hackernews.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codex.hackernews.data.model.Comment
import com.codex.hackernews.data.model.Item
import com.codex.hackernews.databinding.ItemKomentarBinding
import com.codex.hackernews.databinding.ItemStoryBinding
import com.codex.hackernews.utils.millisToDateTime


class CommentAdapter(
) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private val listItem: ArrayList<Comment> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKomentarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(listItem[position])

    }

    override fun getItemCount(): Int = listItem.size

    fun submitData(list:List<Comment>){
        listItem.addAll(list)
        notifyItemInserted(1)
    }

    class ViewHolder(private val binding: ItemKomentarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Comment) {
            binding.komentarText.text = data.text
            binding.komentarBy.text = data.by
            binding.komentarDatetime.text = millisToDateTime(data.time.toLong())
        }
    }
}