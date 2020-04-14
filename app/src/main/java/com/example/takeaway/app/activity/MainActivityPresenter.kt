package com.example.takeaway.app.activity

import com.example.takeaway.app.BasePresenter
import com.example.takeaway.app.navigation.Screen
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<MainActivityView>() {

    fun onBackStackIsEmpty() {
        Thread.sleep(2000L)
        router.newRootScreen(Screen.FeedScreen)
    }
}