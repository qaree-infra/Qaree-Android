package com.muhmmad.qaree.ui.activity.reading_view

import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhmmad.domain.model.BookChapter
import com.muhmmad.qaree.databinding.ReadingViewItemBinding

class ReadingViewAdapter :
    PagingDataAdapter<BookChapter, ReadingViewAdapter.ViewHolder>(chapterComparator) {

    class ViewHolder(private val binding: ReadingViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(path: String) {
            val encodedHtml =
                Base64.encodeToString(path.toByteArray(), Base64.NO_PADDING)
            binding.webView.loadData(encodedHtml, "text/html", "base64")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it.content)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ReadingViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    object chapterComparator : DiffUtil.ItemCallback<BookChapter>() {
        override fun areItemsTheSame(oldItem: BookChapter, newItem: BookChapter): Boolean {
            // Id is unique.
            return oldItem.content == newItem.content
        }

        override fun areContentsTheSame(oldItem: BookChapter, newItem: BookChapter): Boolean {
            return oldItem == newItem
        }
    }
}