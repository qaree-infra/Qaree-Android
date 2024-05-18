package com.muhmmad.qaree.ui.fragment.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Notification
import com.muhmmad.qaree.databinding.NotificationItemBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class NotificationsAdapter : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {
    private val data: ArrayList<Notification> = ArrayList()

    class ViewHolder(val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.apply {
            ivNotification.load(item.image)
            tvNotification.text = item.title
            tvTime.text = item.createdAt
        }
    }

    fun setData(newList: List<Notification>) {
        val diffCallback = DiffUtilCallback(data, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

}