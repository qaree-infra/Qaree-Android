package com.muhmmad.qaree.ui.fragment.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Author
import com.muhmmad.qaree.databinding.AuthorItemBinding

class AuthorsAdapter : RecyclerView.Adapter<AuthorsAdapter.ViewHolder>() {
    private val data = ArrayList<Author>()

    class ViewHolder(val binding: AuthorItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AuthorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            ivAuthor.load(item.avatar)
            tvAuthor.text = item.name
        }
    }

    fun setData(data: List<Author>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}