package com.muhmmad.qaree.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhmmad.domain.model.Shelf
import com.muhmmad.qaree.databinding.ProfileLibraryItemBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class ProfileLibraryAdapter : RecyclerView.Adapter<ProfileLibraryAdapter.ViewHolder>() {
    private val data = ArrayList<Shelf>()

    class ViewHolder(val binding: ProfileLibraryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            ProfileLibraryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            val adapter = BooksAdapter {}
            tvShelfName.text = item.name
            rvShelf.adapter = adapter
            adapter.setData(item.books)
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