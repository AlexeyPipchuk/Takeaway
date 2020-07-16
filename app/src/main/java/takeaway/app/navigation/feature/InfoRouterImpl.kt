package takeaway.app.navigation.feature

import ru.terrakok.cicerone.Router
import takeaway.app.navigation.Screen
import takeaway.feature_info.presentation.InfoRouter
import javax.inject.Inject

class InfoRouterImpl @Inject constructor(
    private val router: Router
) : InfoRouter {

    override fun backToStartPoint() {
        router.backTo(null)
    }

    override fun toPrivacyPolicy() {
        router.navigateTo(Screen.PrivacyPolicyScreen)
    }
}