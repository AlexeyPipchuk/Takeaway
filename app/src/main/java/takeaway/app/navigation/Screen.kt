package takeaway.app.navigation

import androidx.fragment.app.Fragment
import takeaway.feature.feed.ui.FeedFragment
import takeaway.feature.info.ui.InfoFragment

sealed class Screen(fragment: Fragment) : BaseScreen(fragment) {

    object FeedScreen : Screen(FeedFragment.getInstance())
    object InfoScreen : Screen(InfoFragment.getInstance())
}