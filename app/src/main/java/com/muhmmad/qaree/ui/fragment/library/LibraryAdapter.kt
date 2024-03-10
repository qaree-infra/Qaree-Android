package com.muhmmad.qaree.ui.fragment.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Shelf
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.LibraryItemBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class LibraryAdapter : RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {
    private val data = ArrayList<Shelf>()

    class ViewHolder(val binding: LibraryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LibraryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            val ctx = root.context
            tvShelf.text = item.name
            tvBooksNumber.text = ctx.getString(R.string.books_number, item.books.size.toString())
            if (item.books.isNotEmpty()) {
                ivFirst.load(item.books[0].cover.path)
                if (item.books.size >= 2) {
                    ivSecond.load(item.books[1].cover.path)
                }
                if (item.books.size >= 3) {
                    ivThird.load(item.books[2].cover.path)
                }
            }
        }
    }

    fun setData(newData: List<Shelf>) {
        val diffCallback = DiffUtilCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }
}