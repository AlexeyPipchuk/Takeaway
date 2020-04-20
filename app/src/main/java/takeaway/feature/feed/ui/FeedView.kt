package takeaway.feature.feed.ui

import takeaway.app.TakeawayView
import takeaway.feature.feed.presentation.CafeItem

interface FeedView : TakeawayView {

    fun setFeed(cafeList: List<CafeItem>)

    fun showProgress()

    fun hideProgress()

    fun showEmptySearchResult()

    fun hideEmptySearchResult()

    fun clearSearchQuery()
}