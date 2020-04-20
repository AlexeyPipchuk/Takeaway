package com.example.takeaway.app.navigation

import androidx.fragment.app.Fragment
import com.example.takeaway.feature.feed.ui.FeedFragment
import com.example.takeaway.feature.info.ui.InfoFragment

sealed class Screen(fragment: Fragment) : BaseScreen(fragment) {

    object FeedScreen : Screen(FeedFragment.getInstance())
    object InfoScreen : Screen(InfoFragment.getInstance())
}