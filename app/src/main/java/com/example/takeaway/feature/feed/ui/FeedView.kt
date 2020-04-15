package com.example.takeaway.feature.feed.ui

import com.example.takeaway.app.TakeawayView
import com.example.takeaway.feature.feed.model.CafeItem

interface FeedView : TakeawayView {

    fun setFeed(cafeList: List<CafeItem>)
}