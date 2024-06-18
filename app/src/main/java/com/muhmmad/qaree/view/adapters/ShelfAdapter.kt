package com.muhmmad.qaree.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.muhmmad.domain.model.Book
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.ShelfItemBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class ShelfAdapter(
    private val onReadClick: (id: String) -> Unit,
    private val onBookInfoClick: (id: String) -> Unit,
    private val onRemoveClick: (id: String) -> Unit
) : RecyclerView.Adapter<ShelfAdapter.ViewHolder>() {
    private val data = ArrayList<Book>()

    class ViewHolder(val binding: ShelfItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            ShelfItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            val ctx = root.context
            ivBook.load(item.cover.path) {
                transformations(RoundedCornersTransformation(10F))
            }
            tvBook.text = item.name
            tvAuthor.text = item.author?.name
            ratingBar.rating = item.avgRating.toFloat()
            progressBar.progress = item.readingProgress
            tvProgress.text = ctx.getString(R.string.reading_percent, item.readingProgress.toString())
            btnRead.setOnClickListener {
                onReadClick(item.id)
            }
            btnBookInfo.setOnClickListener {
                onBookInfoClick(item.id)
            }
            btnRemove.setOnClickListener {
                onRemoveClick(item.id)
            }
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