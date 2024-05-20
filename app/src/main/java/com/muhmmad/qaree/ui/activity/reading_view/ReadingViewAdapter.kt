package com.muhmmad.qaree.ui.activity.reading_view

import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhmmad.domain.model.BookChapter
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.qaree.databinding.ReadingViewItemBinding

class ReadingViewAdapter :
    PagingDataAdapter<NetworkResponse<BookChapter>, ReadingViewAdapter.ViewHolder>(ChapterComparator) {
    class ViewHolder(private val binding: ReadingViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(path: String) {
            val encodedHtml =
                Base64.encodeToString(path.toByteArray(), Base64.NO_PADDING)
            binding.webView.loadData(encodedHtml, "text/html", "base64")
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it.data?.content ?: "")
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

    object ChapterComparator : DiffUtil.ItemCallback<NetworkResponse<BookChapter>>() {
        override fun areItemsTheSame(
            oldItem: NetworkResponse<BookChapter>,
            newItem: NetworkResponse<BookChapter>
        ): Boolean = oldItem.data == newItem.data

        override fun areContentsTheSame(
            oldItem: NetworkResponse<BookChapter>,
            newItem: NetworkResponse<BookChapter>
        ): Boolean = oldItem == newItem
    }
}