package com.muhmmad.qaree.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Shelf
import com.muhmmad.qaree.databinding.AddToShelfLayoutBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class DialogAddToShelfAdapter(private val onClick: (id: String) -> Unit) :
    RecyclerView.Adapter<DialogAddToShelfAdapter.ViewHolder>() {
    private val data = ArrayList<Shelf>()

    class ViewHolder(val binding: AddToShelfLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            AddToShelfLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            tvShelf.text = item.name
            if (item.books.isNotEmpty()) {
                ivShelf.load(item.books[0].cover.path)
            }

            root.setOnClickListener {
                onClick(item.id)
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