package takeaway.feature.confirmation.presentation

import takeaway.app.TakeawayView

interface ConfirmationView: TakeawayView {

    fun setOrderId(orderId: String)
}