package takeaway.app.navigation.feature

import ru.terrakok.cicerone.Router
import takeaway.app.navigation.Screen
import takeaway.feature_feed.feed.presentation.FeedRouter
import takeaway.shared_cafe.domain.entity.Cafe
import javax.inject.Inject

class FeedRouterImpl @Inject constructor(
    private val router: Router
) : FeedRouter {

    override fun toErrorScreen() {
        router.newRootScreen(Screen.NoInternetScreen)
    }

    override fun toInfoScreen() {
        router.navigateTo(Screen.InfoScreen)
    }

    override fun toAddNewCafeScreen() {
        router.navigateTo(Screen.AddCafeScreen)
    }

    override fun toCafeScreen(cafe: Cafe) {
        router.navigateTo(Screen.CafeScreen(cafe))
    }
}