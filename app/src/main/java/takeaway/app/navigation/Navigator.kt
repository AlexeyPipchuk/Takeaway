package takeaway.app.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.takeaway.R
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import takeaway.feature_basket.ui.BasketFragment
import takeaway.feature_feed.feed.ui.FeedFragment
import takeaway.feature_info.ui.InfoFragment

class Navigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager,
    containerId: Int
) : SupportAppNavigator(activity, fragmentManager, containerId) {

    override fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction
    ) {

        if (nextFragment == null) return

        val horizontalFlowList = listOf(
            takeaway.feature_feed.feed.ui.FeedFragment::class,
            InfoFragment::class,
            BasketFragment::class
        )

        //val verticalFlowList = listOf()

        //val lastInFlowList = listOf()

        when {
            horizontalFlowList.contains(nextFragment::class) -> {
                fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
            }
//            verticalFlowList.contains(nextFragment::class) -> {
//                fragmentTransaction.setCustomAnimations(
//                    R.anim.slide_in_up,
//                    0,
//                    0,
//                    R.anim.slide_out_down
//                )
//            }
//            lastInFlowList.contains(nextFragment::class) -> {
//                fragmentTransaction.setCustomAnimations(
//                    R.anim.slide_in_right,
//                    R.anim.slide_out_left,
//                    R.anim.fade_in,
//                    R.anim.slide_out_down
//                )
//            }
        }

        super.setupFragmentTransaction(command, currentFragment, nextFragment, fragmentTransaction)
    }
}