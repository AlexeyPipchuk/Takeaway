package takeaway.feature_feed.feed.presentation

import base.TakeawayView
import takeaway.feature_feed.feed.presentation.model.FeedItem

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