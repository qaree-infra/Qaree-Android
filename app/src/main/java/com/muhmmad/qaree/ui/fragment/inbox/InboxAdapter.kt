package com.muhmmad.qaree.ui.fragment.inbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Chat
import com.muhmmad.qaree.databinding.InboxLayoutBinding
import com.muhmmad.qaree.utils.DateUtils.getMessageDate
import com.muhmmad.qaree.utils.DiffUtilCallback

class InboxAdapter(private val onClick: (chat: Chat) -> Unit) :
    RecyclerView.Adapter<InboxAdapter.ViewHolder>() {
    private val data = ArrayList<Chat>()

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

            ivBook.load(item.getImage())
            tvName.text = item.getName()
            tvLastMessage.text = item.lastMessage.content
            tvLastMessageTime.text = getMessageDate(ctx, item.lastMessage.createdAt)

            root.setOnClickListener {
                onClick(item)
            }
        }
    }

    fun setData(newData: List<Chat>) {
        val diffCallback = DiffUtilCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }
}