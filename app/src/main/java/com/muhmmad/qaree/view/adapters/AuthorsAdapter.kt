package com.muhmmad.qaree.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.User
import com.muhmmad.qaree.databinding.AuthorItemBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class AuthorsAdapter(private val onClick: (User) -> Unit) :
    RecyclerView.Adapter<AuthorsAdapter.ViewHolder>() {
    private val data = ArrayList<User>()

    class ViewHolder(val binding: AuthorItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AuthorItemBinding.inflate(
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
            ivAuthor.load(item.avatar?.path)
            tvAuthor.text = item.name
            root.setOnClickListener {
                onClick(item)
            }
        }
    }

    fun setData(newData: List<User>) {
        val diffCallback = DiffUtilCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }
}