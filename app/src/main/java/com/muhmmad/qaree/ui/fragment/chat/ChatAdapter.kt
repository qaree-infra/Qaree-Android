package com.muhmmad.qaree.ui.fragment.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muhmmad.domain.model.Message
import com.muhmmad.qaree.databinding.ReceiverLayoutBinding
import com.muhmmad.qaree.databinding.SenderLayoutBinding

class ChatAdapter(private val userId: String) :
    ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallBack()) {
    // private val data: ArrayList<Message> = ArrayList()

    class SenderViewHolder(val binding: SenderLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    class ReceiverViewHolder(val binding: ReceiverLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    class MessageDiffCallBack : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
            oldItem._id == newItem._id

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == UserType.SENDER.ordinal) SenderViewHolder(
            SenderLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        else ReceiverViewHolder(
            ReceiverLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (getItemViewType(position) == UserType.SENDER.ordinal) {
            holder as SenderViewHolder
            holder.binding.apply {
                textView.text = message.content
            }
        } else {
            holder as ReceiverViewHolder
            holder.binding.apply {
                textView.text = message.content
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).sender._id == userId) UserType.RECEIVER.ordinal
        else UserType.SENDER.ordinal

//    fun setData(newData: Chat) {
//        val diffCallback = DiffUtilCallback(data, newData.messages)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        data.clear()
//        data.addAll(newData.messages)
//        diffResult.dispatchUpdatesTo(this)
//    }

//    fun addMessage(message: Message) {
//        val newData = data
//        newData.add(message)
//        val diffCallback = DiffUtilCallback(data, newData)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        diffResult.dispatchUpdatesTo(this)
//    }

    enum class UserType {
        SENDER,
        RECEIVER
    }
}

private const val TAG = "ChatAdapter"