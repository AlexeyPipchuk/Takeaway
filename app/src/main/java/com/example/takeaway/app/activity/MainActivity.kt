package com.example.takeaway.app.activity

import android.os.Bundle
import com.example.takeaway.R
import com.example.takeaway.app.navigation.Navigator
import com.example.takeaway.app.navigation.Screen
import dagger.android.support.DaggerAppCompatActivity
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity @Inject constructor() : DaggerAppCompatActivity(), MainActivityView {

    @Inject
    lateinit var presenter: MainActivityPresenter

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val navigator: SupportAppNavigator by lazy {
        Navigator(this, supportFragmentManager, R.id.activityContentFrame)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)

        router.newRootScreen(Screen.FeedScreen)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}
