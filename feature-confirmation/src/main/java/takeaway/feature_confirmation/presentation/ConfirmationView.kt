package takeaway.feature_confirmation.presentation

import base.TakeawayView

interface ConfirmationView: TakeawayView {

    fun setOrderId(orderId: String)
}