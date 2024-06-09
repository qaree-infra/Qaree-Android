package com.muhmmad.qaree.ui.fragment.book_info.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhmmad.domain.model.Card
import com.muhmmad.qaree.databinding.CardItemBinding

class CardsAdapter : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {
    private val data = ArrayList<Card>()

    class ViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            tvName.text = item.name
            tvNumber.text = item.number
            radioButton.isChecked = item.isChecked
            radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) checkedItem(position)
            }
        }
    }

    fun setData(newData: List<Card>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    private fun checkedItem(position: Int) {
        for (item in data) {
            item.isChecked = false
        }
        data[position].isChecked = true
        notifyDataSetChanged()
    }

    fun getCheckedItem(): Card? {
        for (item in data) {
            if (item.isChecked) return item
        }
        return null
    }
}