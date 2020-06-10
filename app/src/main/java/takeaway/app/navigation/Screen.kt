package takeaway.app.navigation

import androidx.fragment.app.Fragment
import takeaway.feature.addcafe.ui.AddCafeFragment
import takeaway.feature.basket.ui.BasketFragment
import takeaway.feature.cafe.ui.CafeFragment
import takeaway.feature.confirmation.ui.ConfirmationFragment
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.feature.feed.ui.FeedFragment
import takeaway.feature.info.ui.InfoFragment
import takeaway.feature.order.registration.ui.OrderRegistrationFragment
import takeaway.feature.splash.ui.SplashFragment
import takeaway.feature.success.ui.SuccessFragment
import takeaway.shared.nointernet.ui.NoInternetFragment
import takeaway.shared.order.registration.domain.entity.OrderSketch
import takeaway.shared.privacy.policy.ui.PrivacyPolicyFragment

sealed class Screen(fragment: Fragment) : BaseScreen(fragment) {

    object SplashScreen : Screen(SplashFragment.getInstance())
    data class FeedScreen(val noInternet: Boolean = false) :
        Screen(FeedFragment.getInstance(noInternet))

    object InfoScreen : Screen(InfoFragment.getInstance())
    data class CafeScreen(val cafe: Cafe) : Screen(CafeFragment.getInstance(cafe))
    object BasketScreen : Screen(BasketFragment.getInstance())
    object PrivacyPolicyScreen : Screen(PrivacyPolicyFragment.getInstance())

    data class OrderRegistrationScreen(val orderSketch: OrderSketch) :
        Screen(OrderRegistrationFragment.getInstance(orderSketch))

    data class ConfirmationScreen(val orderId: String) :
        Screen(ConfirmationFragment.getInstance(orderId))

    data class SuccessScreen(val orderId: String) :
        Screen(SuccessFragment.getInstance(orderId))

    object AddCafeScreen : Screen(AddCafeFragment.getInstance())
    object NoInternetScreen : Screen(NoInternetFragment.getInstance())
}