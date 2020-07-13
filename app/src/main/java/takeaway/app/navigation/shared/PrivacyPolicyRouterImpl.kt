package takeaway.app.navigation.shared

import ru.terrakok.cicerone.Router
import takeaway.shared_privacy_policy.presentation.PrivacyPolicyRouter
import javax.inject.Inject

class PrivacyPolicyRouterImpl @Inject constructor(
    private val router: Router
) : PrivacyPolicyRouter {

    override fun back() {
        router.exit()
    }
}