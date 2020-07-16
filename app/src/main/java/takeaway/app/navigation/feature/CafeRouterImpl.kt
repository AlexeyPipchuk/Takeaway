package takeaway.app.navigation.feature

import ru.terrakok.cicerone.Router
import takeaway.app.navigation.Screen
import takeaway.feature_cafe.cafe.presentation.CafeRouter
import javax.inject.Inject

class CafeRouterImpl @Inject constructor(
    private val router: Router
) : CafeRouter {

    override fun toError() {
        router.newRootScreen(Screen.FeedScreen(noInternet = true))
    }

    override fun toBasket() {
        router.navigateTo(Screen.BasketScreen)
    }

    override fun back() {
        router.backTo(null)
    }
}