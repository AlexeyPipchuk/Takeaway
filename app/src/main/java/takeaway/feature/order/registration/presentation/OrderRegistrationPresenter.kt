package takeaway.feature.order.registration.presentation

import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.navigation.Screen
import javax.inject.Inject

class OrderRegistrationPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<OrderRegistrationView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.setPrivacyPolicyText()
    }

    fun onBackToBasketButtonClicked() {
        router.exit()
    }

    fun onCreateOrderButtonClicked() {

    }

    fun onPrivacyPolicyClicked() {
        router.navigateTo(Screen.PrivacyPolicyScreen)
    }
}