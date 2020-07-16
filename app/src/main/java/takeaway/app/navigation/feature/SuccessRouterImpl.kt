package takeaway.app.navigation.feature

import ru.terrakok.cicerone.Router
import takeaway.app.navigation.Screen
import takeaway.feature_success.presentation.SuccessRouter
import javax.inject.Inject

class SuccessRouterImpl @Inject constructor(
    private val router: Router
) : SuccessRouter {

    override fun toFeedScreen() {
        router.newRootChain(Screen.FeedScreen())
    }
}