package com.example.takeaway.feature.feed.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.feature.feed.model.CafeItem

class FeedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var cafeList: List<CafeItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        FeedHolder.createInstance(parent)

    override fun getItemCount(): Int = cafeList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FeedHolder) {
            holder.bind(cafeList[position])
        }
    }
}