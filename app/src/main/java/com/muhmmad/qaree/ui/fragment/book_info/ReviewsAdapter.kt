package com.muhmmad.qaree.ui.fragment.book_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhmmad.domain.model.Review
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.ReviewItemBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
    private val data = ArrayList<Review>()

    class ViewHolder(val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            val ctx = root.context
            ratingBar.rating = item.rate
            tvUser.text = ctx.getString(R.string.author_name, item.user.name)
            tvReview.text = item.content
        }
    }

    fun setData(newData: List<Review>) {
        val diffCallback = DiffUtilCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }
}