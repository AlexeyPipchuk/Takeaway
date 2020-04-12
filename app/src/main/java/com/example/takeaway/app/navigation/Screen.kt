package com.example.takeaway.app.navigation

import androidx.fragment.app.Fragment
import com.example.takeaway.feature.ui.FeedFragment

sealed class Screen(fragment: Fragment) : BaseScreen(fragment) {

    object FeedScreen : Screen(FeedFragment.getInstance())
}