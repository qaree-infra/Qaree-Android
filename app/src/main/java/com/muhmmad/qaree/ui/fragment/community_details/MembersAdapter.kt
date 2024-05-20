package com.muhmmad.qaree.ui.fragment.community_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.User
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.MemberLayoutBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class MembersAdapter(private val onClick: (user: User) -> Unit) :
    RecyclerView.Adapter<MembersAdapter.ViewHolder>() {
    private val data = ArrayList<User>()

    class ViewHolder(val binding: MemberLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        MemberLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            if (item.avatar?.path?.isNotBlank() == true) {
                ivUser.load(item.avatar?.path) {
                    placeholder(R.drawable.ic_profile_avatar)
                }
            }

            tvName.text = item.name
            tvBio.text = item.bio

            root.setOnClickListener {
                onClick(item)
            }
        }
    }

    fun setData(newList: List<User>) {
        val diffCallback = DiffUtilCallback(data, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}