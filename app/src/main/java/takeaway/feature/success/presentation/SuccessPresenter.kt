package takeaway.feature.success.presentation

import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.navigation.Screen
import javax.inject.Inject

class SuccessPresenter @Inject constructor(
    private val router: Router,
    private val orderId: String
) : BasePresenter<SuccessView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.setOrderId(orderId)
    }

    fun onFeedButtonClicked() {
        router.newRootChain(Screen.FeedScreen())
    }

    fun onBackClicked() {
        router.newRootChain(Screen.FeedScreen())
    }
}