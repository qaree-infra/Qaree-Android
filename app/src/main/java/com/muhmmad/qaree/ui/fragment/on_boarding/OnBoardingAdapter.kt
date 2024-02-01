package com.muhmmad.qaree.ui.fragment.on_boarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.OnBoardingLayoutBinding

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {
    class ViewHolder(val binding: OnBoardingLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OnBoardingLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.binding.imageView.setImageResource(R.drawable.ic_intro_1)
                holder.binding.tvTitle.text =
                    holder.binding.root.context.getString(R.string.easy_search)
                holder.binding.tvDescription.text =
                    holder.binding.root.context.getString(R.string.search_on_easy_to_find_the_books_with_the_help_of_categories)
            }

            1 -> {
                holder.binding.imageView.setImageResource(R.drawable.ic_intro_2)
                holder.binding.tvTitle.text =
                    holder.binding.root.context.getString(R.string.read_anytime_any_where)
                holder.binding.tvDescription.text =
                    holder.binding.root.context.getString(R.string.read_anytime_and_any_where_using_your_mobile_to_buy_the_books)
            }

            2 -> {
                holder.binding.imageView.setImageResource(R.drawable.ic_intro_3)
                holder.binding.tvTitle.text =
                    holder.binding.root.context.getString(R.string.personal_library)
                holder.binding.tvDescription.text =
                    holder.binding.root.context.getString(R.string.all_the_books_that_you_purchased_will_store_in_the_my_books_it_can_access_by_any_device)
            }
        }
    }
}