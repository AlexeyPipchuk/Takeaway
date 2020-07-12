package takeaway.feature.feed.ui

import base.TakeawayView
import takeaway.feature.feed.presentation.model.FeedItem

interface FeedView : TakeawayView {

    fun setFeed (cafeItemList: List<FeedItem>)

    fun showProgress()

    fun hideProgress()

    fun showEmptySearchResult()

    fun hideEmptySearchResult()

    fun clearSearchQuery()

    fun showNoInternetDialog()

    fun showServiceUnavailable()

    fun showPromoDialog()

    fun openFabMenu()

    fun closeFabMenu()
}