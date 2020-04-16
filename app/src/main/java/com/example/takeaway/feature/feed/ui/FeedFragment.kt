package com.example.takeaway.feature.feed.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import com.example.takeaway.app.BaseFragment
import com.example.takeaway.feature.feed.model.CafeItem
import com.example.takeaway.feature.feed.presentation.FeedPresenter
import kotlinx.android.synthetic.main.feed_fragment.*
import javax.inject.Inject

class FeedFragment : BaseFragment(R.layout.feed_fragment), FeedView {

    companion object {
        fun getInstance(): Fragment = FeedFragment()
    }

    @Inject
    lateinit var presenter: FeedPresenter

    private var adapter: FeedAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initHandlers()
    }

    private fun initHandlers() {
        infoButton.setOnClickListener {
            presenter.onInfoButtonClicked()
        }
    }

    override fun setFeed(cafeList: List<CafeItem>) {
        adapter = FeedAdapter()
        adapter?.cafeList = cafeList

        cafeListRecycler.adapter = adapter
    }
}