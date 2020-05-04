package takeaway.feature.cafe.product.severalcafe.presentation

import takeaway.app.TakeawayView

interface SeveralCafeWarningView : TakeawayView {

    fun closeDialogWithAcceptResult()

    fun closeDialogWithRejectResult()
}