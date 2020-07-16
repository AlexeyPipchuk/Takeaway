package takeaway.feature_feed.promo.presentation

import base.BasePresenter
import javax.inject.Inject

class PromoPresenter @Inject constructor() : BasePresenter<PromoView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.showMotivationText()
    }

    fun onExitButtonClicked() {
        view?.closeDialog()
    }
}