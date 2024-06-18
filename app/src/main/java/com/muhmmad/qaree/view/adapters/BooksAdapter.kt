package com.muhmmad.qaree.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Book
import com.muhmmad.qaree.databinding.NewReleaseItemBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class BooksAdapter(private val onClick: (item: Book) -> Unit) :
    RecyclerView.Adapter<BooksAdapter.ViewHolder>() {
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
            tvAuthor.text = item.author?.name
            ratingBar.numStars = 5
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