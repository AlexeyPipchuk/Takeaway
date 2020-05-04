package takeaway.feature.feed.promo.presentation

import takeaway.app.BasePresenter
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