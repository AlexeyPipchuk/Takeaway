package com.example.takeaway.feature.feed.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.R
import com.example.takeaway.feature.feed.presentation.CafeItem
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedHolder(
    view: View,
    private val context: Context
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createInstance(parent: ViewGroup, context: Context) = FeedHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.feed_item,
                parent,
                false
            ),
            context
        )
    }

    fun bind(cafeItem: CafeItem) {
        itemView.cafeName.text = cafeItem.cafeName
        val a = cafeItem.deliveryDiscount
        val formatted = context.getString(R.string.delivery_discount)
        itemView.deliveryDiscount.text =
            context.getString(R.string.delivery_discount).format(cafeItem.deliveryDiscount.toString())
        itemView.deliveryFreeFrom.text =
            context.getString(R.string.delivery_free_from).format(cafeItem.deliveryFreeFrom.toString())
    }
}