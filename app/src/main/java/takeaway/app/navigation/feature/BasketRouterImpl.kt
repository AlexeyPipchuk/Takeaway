package takeaway.app.navigation.feature

import domain.entity.OrderSketch
import ru.terrakok.cicerone.Router
import takeaway.app.navigation.Screen
import takeaway.feature_basket.presentation.BasketRouter
import javax.inject.Inject

class BasketRouterImpl @Inject constructor(
    private val router: Router
) : BasketRouter {

    override fun backToCafe() {
        router.exit()
    }

    override fun toOrderRegistration(orderSketch: OrderSketch) {
        router.navigateTo(Screen.OrderRegistrationScreen(orderSketch))
    }
}