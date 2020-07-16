package takeaway.feature_cafe.severalcafe.presentation

import base.TakeawayView

interface SeveralCafeWarningView : TakeawayView {

    fun closeDialogWithAcceptResult()

    fun closeDialogWithRejectResult()
}