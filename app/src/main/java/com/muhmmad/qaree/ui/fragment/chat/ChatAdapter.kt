package com.muhmmad.qaree.ui.fragment.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Chat
import com.muhmmad.domain.model.Message
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.ReceiverCommunityLayoutBinding
import com.muhmmad.qaree.databinding.ReceiverLayoutBinding
import com.muhmmad.qaree.databinding.SenderCommunityLayoutBinding
import com.muhmmad.qaree.databinding.SenderLayoutBinding
import com.muhmmad.qaree.ui.fragment.CommunityViewModel
import com.muhmmad.qaree.utils.DiffUtilCallback

class ChatAdapter(
    private val userId: String,
    private val viewModel: CommunityViewModel,
    private val isCommunity: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data = ArrayList<Message>()
    private var chat: Chat? = null

    class SenderViewHolder(val binding: SenderLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    class ReceiverViewHolder(val binding: ReceiverLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    class SenderCommunityViewHolder(val binding: SenderCommunityLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ReceiverCommunityViewHolder(val binding: ReceiverCommunityLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

//    class MessageDiffCallBack : DiffUtil.ItemCallback<Message>() {
//        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
//            oldItem._id == newItem._id
//
//        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
//            oldItem == newItem
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            UserType.SENDER.ordinal -> SenderViewHolder(
                SenderLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            UserType.SENDER_COMMUNITY.ordinal -> SenderCommunityViewHolder(
                SenderCommunityLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            UserType.RECEIVER_COMMUNITY.ordinal -> ReceiverCommunityViewHolder(
                ReceiverCommunityLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> ReceiverViewHolder(
                ReceiverLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = data[position]
        if (position == data.size - 6) {
            if (chat?.currentPage!! < chat?.numberOfPages!!) {
                var page = chat?.currentPage ?: 0
                page++
                viewModel.getMessages(message.room, page = page)
            }
        }
        if (getItemViewType(position) == UserType.SENDER.ordinal) {
            holder as SenderViewHolder
            holder.binding.apply {
                textView.text = message.content
            }
        } else if (getItemViewType(position) == UserType.SENDER_COMMUNITY.ordinal) {
            holder as SenderCommunityViewHolder

            holder.binding.apply {
                textView.text = message.content
                val image = message.sender?.avatar?.path ?: ""
                if (image.isEmpty()) ivUser.load(R.drawable.ic_profile_avatar)
                else {
                    ivUser.load(image) {
                        placeholder(R.drawable.ic_profile_avatar)
                    }
                }
            }
        } else if (getItemViewType(position) == UserType.RECEIVER_COMMUNITY.ordinal) {
            holder as ReceiverCommunityViewHolder

            holder.binding.apply {
                textView.text = message.content
                ivUser.load(message.sender?.avatar?.path ?: "") {
                    placeholder(R.drawable.ic_profile_avatar)
                }
            }
        } else {
            holder as ReceiverViewHolder
            holder.binding.apply {
                textView.text = message.content
            }
        }
    }


    override fun getItemViewType(position: Int): Int =
        if (isCommunity) {
            if (data[position].sender == null || data[position].sender?._id != userId) UserType.SENDER_COMMUNITY.ordinal
            else UserType.RECEIVER_COMMUNITY.ordinal
        } else {
            if (data[position].sender == null || data[position].sender?._id != userId) UserType.SENDER.ordinal
            else UserType.RECEIVER.ordinal
        }

    override fun getItemCount(): Int = data.size

    fun addMessage(message: Message) {
        data.add(message)
        notifyDataSetChanged()
//        val diffCallback = DiffUtilCallback(data,data)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        //data.add(message)
//        diffResult.dispatchUpdatesTo(this)
    }

    fun addData(chat: Chat) {
        Log.i(TAG, "ADD Data")
        this.chat = chat
        data.addAll(chat.messages)
        notifyDataSetChanged()
//        val diffCallback = DiffUtilCallback(data, data)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
////        data.clear()
////        data.addAll(newList)
//        diffResult.dispatchUpdatesTo(this)
    }

    fun setData(chat: Chat) {
        Log.i(TAG, "set Data")
        this.chat = chat
        val diffCallback = DiffUtilCallback(data, chat.messages)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(chat.messages)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getDataSize(): Int = data.size

    enum class UserType {
        SENDER,
        RECEIVER,
        SENDER_COMMUNITY,
        RECEIVER_COMMUNITY
    }
}

private const val TAG = "ChatAdapter"