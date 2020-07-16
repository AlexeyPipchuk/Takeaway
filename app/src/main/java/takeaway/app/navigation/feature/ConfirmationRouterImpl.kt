package takeaway.app.navigation.feature

import ru.terrakok.cicerone.Router
import takeaway.app.navigation.Screen
import takeaway.feature_confirmation.presentation.ConfirmationRouter
import javax.inject.Inject

class ConfirmationRouterImpl @Inject constructor(
    private val router: Router
) : ConfirmationRouter {

    override fun toFeedScreen() {
        router.backTo(Screen.FeedScreen())
    }
}