package takeaway.feature_cafe.severalcafe.presentation

import base.BasePresenter
import javax.inject.Inject

class SeveralCafeWarningDialogPresenter @Inject constructor() :
    BasePresenter<SeveralCafeWarningView>() {

    fun onClearBasketClicked() {
        view?.closeDialogWithAcceptResult()
    }

    fun onCancelClicked() {
        view?.closeDialogWithRejectResult()
    }
}