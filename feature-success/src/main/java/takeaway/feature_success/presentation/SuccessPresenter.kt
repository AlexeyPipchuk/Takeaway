package takeaway.feature_success.presentation

import base.BasePresenter
import javax.inject.Inject

class SuccessPresenter @Inject constructor(
    private val router: SuccessRouter,
    private val orderId: String
) : BasePresenter<SuccessView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.setOrderId(orderId)
    }

    fun onFeedButtonClicked() {
        router.toFeedScreen()
    }

    fun onBackClicked() {
        router.toFeedScreen()
    }
}