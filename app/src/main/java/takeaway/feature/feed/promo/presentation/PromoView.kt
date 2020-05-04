package takeaway.feature.feed.promo.presentation

import takeaway.app.TakeawayView

interface PromoView : TakeawayView {

    fun closeDialog()

    fun showMotivationText()
}