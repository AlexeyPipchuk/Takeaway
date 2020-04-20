package takeaway.app.activity

import takeaway.app.BasePresenter
import takeaway.app.navigation.Screen
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<MainActivityView>() {

    fun onBackStackIsEmpty() {
        Thread.sleep(1000L)
        router.newRootScreen(Screen.FeedScreen)
    }
}