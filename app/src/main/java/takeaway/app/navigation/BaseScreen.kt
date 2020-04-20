package com.example.takeaway.app.navigation

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

open class BaseScreen(private val fragment: Fragment) : SupportAppScreen() {

    override fun getFragment(): Fragment = fragment
}