package com.muhmmad.qaree.ui.fragment.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Category
import com.muhmmad.qaree.databinding.CategoriesItemBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private val data = ArrayList<Category>()

    class ViewHolder(val binding: CategoriesItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CategoriesItemBinding.inflate(
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
            val ctx = root.context
            ivCategory.load(item.image)
            tvCategory.text = item.nameEn
        }
    }

    fun setData(newData: List<Category>) {
        val diffCallback = DiffUtilCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }
}