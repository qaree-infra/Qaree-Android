package com.muhmmad.qaree.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.muhmmad.domain.model.Room
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.InboxLayoutBinding
import com.muhmmad.qaree.utils.DateUtils.getMessageDate
import com.muhmmad.qaree.utils.DiffUtilCallback

class InboxAdapter(private val onClick: (chat: Room) -> Unit) : RecyclerView.Adapter<InboxAdapter.ViewHolder>() {
    private val data = ArrayList<Room>()

    class ViewHolder(val binding: InboxLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        InboxLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            val ctx = root.context

            try {
                ivBook.load(item.getImage()) {
                    crossfade(true)
                    placeholder(R.drawable.ic_logo)
                    transformations(CircleCropTransformation())
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            tvName.text = item.getName()
            tvLastMessage.text = item.lastMessage.content
            tvLastMessageTime.text = getMessageDate(ctx, item.lastMessage.createdAt ?: "")

            root.setOnClickListener {
                onClick(item)
            }
        }
    }

    fun setData(newData: List<Room>) {
        val diffCallback = DiffUtilCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }
}