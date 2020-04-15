package com.example.takeaway.feature.feed.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.R
import com.example.takeaway.feature.feed.model.CafeItem
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createInstance(parent: ViewGroup) = FeedHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.feed_item,
                parent,
                false
            )
        )
    }

    fun bind(cafeItem: CafeItem) {
        itemView.cafeName.text = cafeItem.cafeName
        itemView.pickupDiscount.text = cafeItem.pickupDiscount
        itemView.deliveryInfo.text = cafeItem.deliveryInfo
    }
}