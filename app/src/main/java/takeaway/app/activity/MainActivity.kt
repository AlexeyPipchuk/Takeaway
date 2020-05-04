package takeaway.app.activity

import android.os.Bundle
import android.view.WindowManager
import com.example.takeaway.R
import dagger.android.support.DaggerAppCompatActivity
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import takeaway.app.navigation.Navigator
import javax.inject.Inject

class MainActivity @Inject constructor() : DaggerAppCompatActivity(), MainActivityView {

    @Inject
    lateinit var presenter: MainActivityPresenter

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator: SupportAppNavigator by lazy {
        Navigator(this, supportFragmentManager, R.id.activityContentFrame)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        presenter.attachView(this)
        setContentView(R.layout.activity_main)

        checkFirstStart()
    }

    private fun checkFirstStart() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            presenter.onBackStackIsEmpty()
        }
    }

    override fun showStatusBar() {
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun hideStatusBar() {
        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}