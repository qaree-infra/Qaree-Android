package com.muhmmad.qaree.ui.fragment.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhmmad.domain.model.Message
import com.muhmmad.qaree.databinding.ReceiverLayoutBinding
import com.muhmmad.qaree.databinding.SenderLayoutBinding
import com.muhmmad.qaree.ui.fragment.CommunityViewModel
import com.muhmmad.qaree.utils.DiffUtilCallback

class ChatAdapter(
    private val userId: String,
    private val viewModel: CommunityViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal val data = ArrayList<Message>()

    class SenderViewHolder(val binding: SenderLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    class ReceiverViewHolder(val binding: ReceiverLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

//    class MessageDiffCallBack : DiffUtil.ItemCallback<Message>() {
//        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
//            oldItem._id == newItem._id
//
//        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
//            oldItem == newItem
//    }

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
        val message = data[position]
        Log.i(TAG, position.toString())
        if (position == data.size - 6) {
            Log.i(TAG, "DONE")
            viewModel.getMessages(message.room)
        }

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
        if (data[position].sender == null || data[position].sender._id != userId) UserType.SENDER.ordinal
        else UserType.RECEIVER.ordinal

    override fun getItemCount(): Int = data.size

    fun addMessage(message: Message) {
        data.add(message)
        notifyDataSetChanged()
//        val diffCallback = DiffUtilCallback(data,data)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        //data.add(message)
//        diffResult.dispatchUpdatesTo(this)
    }

    fun addData(newList: List<Message>) {
        data.addAll(newList)
        notifyDataSetChanged()
//        val diffCallback = DiffUtilCallback(data, data)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
////        data.clear()
////        data.addAll(newList)
//        diffResult.dispatchUpdatesTo(this)
    }

    fun setData(newList: List<Message>) {
        val diffCallback = DiffUtilCallback(data, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }


    enum class UserType {
        SENDER,
        RECEIVER
    }
}

private const val TAG = "ChatAdapter"