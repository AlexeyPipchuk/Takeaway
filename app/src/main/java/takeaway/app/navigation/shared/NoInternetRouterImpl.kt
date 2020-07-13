package takeaway.app.navigation.shared

import ru.terrakok.cicerone.Router
import takeaway.app.navigation.Screen
import takeaway.shared_error.presentation.NoInternetRouter
import javax.inject.Inject

class NoInternetRouterImpl @Inject constructor(
    private val router: Router
) : NoInternetRouter {

    override fun toInfoScreen() {
        router.navigateTo(Screen.InfoScreen)
    }

    override fun toFeedScreen() {
        router.replaceScreen(Screen.FeedScreen())
    }
}