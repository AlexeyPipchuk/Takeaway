package takeaway.feature.cafe.product.severalcafe.presentation

import base.TakeawayView

interface SeveralCafeWarningView : TakeawayView {

    fun closeDialogWithAcceptResult()

    fun closeDialogWithRejectResult()
}