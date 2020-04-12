package com.example.takeaway.feature.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import com.example.takeaway.app.BaseFragment
import com.example.takeaway.feature.presentation.FeedPresenter
import javax.inject.Inject

class FeedFragment : BaseFragment(R.layout.feed_fragment), FeedView {

    companion object {
        fun getInstance(): Fragment = FeedFragment()
    }

    @Inject
    lateinit var presenter: FeedPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initToolbar()
    }

    private fun initToolbar() {
        initToolbar(R.string.feed_title)
    }
}