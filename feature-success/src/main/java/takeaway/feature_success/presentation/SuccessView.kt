package takeaway.feature_success.presentation

import base.TakeawayView

interface SuccessView : TakeawayView {

    fun setOrderId(orderId: String)
}