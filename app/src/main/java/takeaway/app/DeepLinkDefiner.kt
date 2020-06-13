package takeaway.app

import takeaway.app.navigation.Screen
import takeaway.feature.splash.domain.entity.DeepLink
import takeaway.feature.splash.domain.entity.DeepLinks

class DeepLinkDefiner {

    operator fun invoke(deepLink: DeepLink): Screen? =
        with(deepLink) {
            when (path) {
                DeepLinks.SUCCESS_LINK.path -> Screen.SuccessScreen(params.values.first())
                else -> null
            }
        }
}