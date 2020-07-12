package takeaway.app.activity

import android.content.Intent
import base.BasePresenter
import ru.terrakok.cicerone.Router
import takeaway.app.navigation.Screen
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<MainActivityView>() {

    fun onBackStackIsEmpty(intent: Intent) {
        router.newRootScreen(Screen.SplashScreen(intent.data))
    }
}