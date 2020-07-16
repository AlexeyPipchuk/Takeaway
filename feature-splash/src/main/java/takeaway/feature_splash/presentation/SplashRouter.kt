package takeaway.feature_splash.presentation

import takeaway.feature_splash.domain.entity.DeepLink

interface SplashRouter {

    fun toNoInternet()

    fun toFeed()

    fun toScreenOnDeepLink(deepLink: DeepLink)
}