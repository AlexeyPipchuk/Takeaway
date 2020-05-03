package takeaway.feature.basket.ui

import takeaway.app.TakeawayView
import takeaway.feature.feed.domain.entity.Cafe

interface BasketView : TakeawayView {

    fun showOrderCafeInfo(cafe: Cafe)

    fun showEmptyContent()
}