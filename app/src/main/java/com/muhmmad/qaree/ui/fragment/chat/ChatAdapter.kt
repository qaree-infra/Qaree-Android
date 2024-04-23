package com.muhmmad.qaree.ui.fragment.chat

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhmmad.qaree.databinding.ReceiverLayoutBinding
import com.muhmmad.qaree.databinding.SenderLayoutBinding

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class SenderViewHolder(val binding: SenderLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    class ReceiverViewHolder(val binding: ReceiverLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}