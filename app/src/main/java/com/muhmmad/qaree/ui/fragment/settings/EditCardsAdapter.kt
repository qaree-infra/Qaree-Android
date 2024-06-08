package com.muhmmad.qaree.ui.fragment.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhmmad.domain.model.Card
import com.muhmmad.qaree.databinding.EditCardItemLayoutBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

class EditCardsAdapter(
    private val onCardDelete: (Card) -> Unit
) : RecyclerView.Adapter<EditCardsAdapter.ViewHolder>() {
    private val data = ArrayList<Card>()

    class ViewHolder(val binding: EditCardItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        EditCardItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            val ctx = root.context
            //    ivCard.load(AppCompatResources.getDrawable(ctx, item.image))
            tvName.text = item.name
            tvNumber.text = item.number
            ivDelete.setOnClickListener {
                onCardDelete(item)
                updateDataWhenDelete(item)
            }
        }
    }

    fun setData(newData: List<Card>) {
        val diffCallback = DiffUtilCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun updateDataWhenDelete(item: Card) {
        data.remove(item)
        notifyDataSetChanged()
    }
}