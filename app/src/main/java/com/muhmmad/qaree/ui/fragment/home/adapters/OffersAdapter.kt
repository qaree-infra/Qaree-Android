package com.muhmmad.qaree.ui.fragment.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Offer
import com.muhmmad.qaree.databinding.OfferLayoutBinding

class OffersAdapter : RecyclerView.Adapter<OffersAdapter.ViewHolder>() {
    private val data = ArrayList<Offer>()

    class ViewHolder(val binding: OfferLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OfferLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            ivOffer.load(item.image)
            tvBookCategory.text = "Noval"
            tvBookName.text = item.bookName
            tvWriterName.text = item.authorName
            tvPrice.text = "${item.price} LE"
            btnOffer.text = "${item.percentage}$ off"
        }
    }

    fun setData(data: List<Offer>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}