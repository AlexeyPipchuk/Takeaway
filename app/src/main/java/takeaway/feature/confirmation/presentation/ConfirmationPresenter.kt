package takeaway.feature.confirmation.presentation

import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.navigation.Screen
import javax.inject.Inject

class ConfirmationPresenter @Inject constructor(
    private val router: Router,
    private val orderId: String
) : BasePresenter<ConfirmationView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.setOrderId(orderId)
    }

    fun onToMainPageBackClicked() {
        router.backTo(Screen.FeedScreen)
    }

    fun onBackClicked() {
        router.backTo(Screen.FeedScreen)
    }
}