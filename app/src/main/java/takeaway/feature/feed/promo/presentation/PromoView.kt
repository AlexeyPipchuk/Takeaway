package takeaway.feature.feed.promo.presentation

import base.TakeawayView

interface PromoView : TakeawayView {

    fun closeDialog()

    fun showMotivationText()
}