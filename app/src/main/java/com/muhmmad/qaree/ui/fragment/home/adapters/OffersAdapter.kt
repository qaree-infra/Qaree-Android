package com.muhmmad.qaree.ui.fragment.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.muhmmad.domain.model.Offer
import com.muhmmad.qaree.R
import com.muhmmad.qaree.databinding.OfferLayoutBinding
import com.muhmmad.qaree.utils.DiffUtilCallback

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
            val ctx = root.context
            ivOffer.load(item.book.cover.path)
            tvBookCategory.text = item.book.categories?.get(0)?.nameEn
            tvBookName.text = item.book.name
            tvWriterName.text = item.book.author?.name
            tvPrice.text = ctx.getString(R.string.offer_price, item.book.price.toString())
            btnOffer.text = ctx.getString(R.string.offer_percent, item.percent.toString() + "%")
        }
    }

    fun setData(newData: List<Offer>) {
        val list = newData.filter { it.book.cover.path.isNotEmpty() }
        val diffCallback = DiffUtilCallback(data, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}