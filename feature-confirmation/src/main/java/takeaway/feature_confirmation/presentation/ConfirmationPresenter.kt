package takeaway.feature_confirmation.presentation

import base.BasePresenter
import javax.inject.Inject

class ConfirmationPresenter @Inject constructor(
    private val router: ConfirmationRouter,
    private val orderId: String
) : BasePresenter<ConfirmationView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.setOrderId(orderId)
    }

    fun onToMainPageBackClicked() {
        router.toFeedScreen()
    }

    fun onBackClicked() {
        router.toFeedScreen()
    }
}