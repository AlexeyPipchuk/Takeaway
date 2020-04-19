package com.example.takeaway.feature.feed.ui

import com.example.takeaway.app.TakeawayView
import com.example.takeaway.feature.feed.presentation.CafeItem

interface FeedView : TakeawayView {

    fun setFeed(cafeList: List<CafeItem>)

    fun showProgress()

    fun hideProgress()

    fun showEmptySearchResult()

    fun hideEmptySearchResult()

    fun clearSearchQuery()
}