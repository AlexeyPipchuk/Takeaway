package takeaway.app.navigation

import takeaway.feature_splash.domain.entity.DeepLink
import takeaway.feature_splash.domain.entity.DeepLinks

class DeepLinkDefiner {

    operator fun invoke(deepLink: DeepLink): Screen? =
        with(deepLink) {
            when (path) {
                DeepLinks.SUCCESS_LINK.path -> Screen.SuccessScreen(params.values.first())
                else -> null
            }
        }
}