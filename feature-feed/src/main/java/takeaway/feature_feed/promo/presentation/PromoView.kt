package takeaway.feature_feed.promo.presentation

import base.TakeawayView

interface PromoView : TakeawayView {

    fun closeDialog()

    fun showMotivationText()
}