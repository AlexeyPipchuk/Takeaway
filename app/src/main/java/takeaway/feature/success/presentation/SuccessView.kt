package takeaway.feature.success.presentation

import takeaway.app.TakeawayView

interface SuccessView : TakeawayView {

    fun setOrderId(orderId: String)
}