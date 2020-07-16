package takeaway.app.navigation.feature

import ru.terrakok.cicerone.Router
import takeaway.app.navigation.DeepLinkDefiner
import takeaway.app.navigation.Screen
import takeaway.feature_splash.domain.entity.DeepLink
import takeaway.feature_splash.presentation.SplashRouter
import javax.inject.Inject

class SplashRouterImpl @Inject constructor(
    private val deepLinkDefiner: DeepLinkDefiner,
    private val router: Router
) : SplashRouter {

    override fun toNoInternet() {
        router.newRootScreen(Screen.NoInternetScreen)
    }

    override fun toFeed() {
        router.newRootScreen(Screen.FeedScreen())
    }

    override fun toScreenOnDeepLink(deepLink: DeepLink) {
        val screen = deepLinkDefiner(deepLink)

        screen?.let {
            router.newRootScreen(screen)
        } ?: toFeed()
    }
}