package com.muhmmad.qaree.ui.fragment.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Book
import com.muhmmad.qaree.databinding.NewReleaseItemBinding

class BooksAdapter : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {
    private val data = ArrayList<Book>()

    class ViewHolder(val binding: NewReleaseItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NewReleaseItemBinding.inflate(
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
            ivBook.load(item.cover.path)
            tvBookName.text = item.name
            tvAuthor.text = item.author.name
//            ratingBar.rating = item
        }
    }

    fun setData(data: List<Book>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}