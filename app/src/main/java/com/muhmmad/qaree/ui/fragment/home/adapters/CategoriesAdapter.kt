package com.muhmmad.qaree.ui.fragment.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import com.muhmmad.domain.model.Category
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.CategoriesItemBinding

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
            ImageRequest.Builder(ctx).data(item.image).target {
                ivCategory.load(it)
                Palette.from(it.toBitmap(40, 40)).generate {
                    val color: Int = it?.lightVibrantSwatch?.bodyTextColor!!
                    root.setCardBackgroundColor(color)
                    root.setBackgroundColor(color)
                }
            }
            ivCategory.load(item.image)
            tvCategory.text = item.nameEn
        }
    }

    fun setData(data: List<Category>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}