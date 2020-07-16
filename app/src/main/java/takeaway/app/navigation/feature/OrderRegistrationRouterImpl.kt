package takeaway.app.navigation.feature

import ru.terrakok.cicerone.Router
import takeaway.app.navigation.Screen
import takeaway.feature_order_registration.presentation.OrderRegistrationRouter
import javax.inject.Inject

class OrderRegistrationRouterImpl @Inject constructor(
    private val router: Router
) : OrderRegistrationRouter {

    override fun toConfirmation(orderId: String) {
        router.navigateTo(Screen.ConfirmationScreen(orderId))
    }

    override fun backToBasket() {
        router.exit()
    }

    override fun backToStartPoint() {
        router.newRootScreen(Screen.FeedScreen(noInternet = true))
    }

    override fun toPrivacyPolicy() {
        router.navigateTo(Screen.PrivacyPolicyScreen)
    }
}