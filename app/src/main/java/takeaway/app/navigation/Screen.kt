package takeaway.app.navigation

import androidx.fragment.app.Fragment
import takeaway.feature.basket.ui.BasketFragment
import takeaway.feature.cafe.ui.CafeFragment
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.feature.feed.ui.FeedFragment
import takeaway.feature.info.ui.InfoFragment
import takeaway.feature.order.registration.ui.OrderRegistrationFragment
import takeaway.feature.splash.ui.SplashFragment
import takeaway.shared.privacy.policy.ui.PrivacyPolicyFragment

sealed class Screen(fragment: Fragment) : BaseScreen(fragment) {

    object SplashScreen : Screen(SplashFragment.getInstance())
    object FeedScreen : Screen(FeedFragment.getInstance())
    object InfoScreen : Screen(InfoFragment.getInstance())
    data class CafeScreen(val cafe: Cafe) : Screen(CafeFragment.getInstance(cafe))
    object BasketScreen : Screen(BasketFragment.getInstance())
    object PrivacyPolicyScreen : Screen(PrivacyPolicyFragment.getInstance())
    object OrderRegistrationScreen : Screen(OrderRegistrationFragment.getInstance())
}