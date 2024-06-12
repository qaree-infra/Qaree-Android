package com.muhmmad.qaree.ui.fragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Book
import com.muhmmad.qaree.databinding.SearchItemBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class SearchAdapter(private val onClick: (Book) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private val data = ArrayList<Book>()

    class ViewHolder(val binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            ivBook.load(item.cover.path)
            tvBookName.text = item.name
            tvWriter.text = item.author?.name
            ratingBar.rating = item.avgRating.toFloat()

            root.setOnClickListener { onClick(item) }
        }
    }

    fun setData(newData: List<Book>) {
        val diffCallback = DiffUtilCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }
}