package takeaway.app.activity

import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.navigation.Screen
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<MainActivityView>() {

    fun onBackStackIsEmpty() {
        router.newRootScreen(Screen.SplashScreen)
    }
}