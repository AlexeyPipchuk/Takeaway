package com.example.takeaway.feature.feed.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import com.example.takeaway.app.BaseFragment
import com.example.takeaway.app.hideKeyboard
import com.example.takeaway.feature.feed.presentation.CafeItem
import com.example.takeaway.feature.feed.presentation.FeedPresenter
import kotlinx.android.synthetic.main.empty_search_result_view.*
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

        initViews()
        initHandlers()
    }

    private fun initViews() {
        swipeContainer.setColorSchemeColors(
            getColor(
                requireContext().resources,
                R.color.colorPrimary,
                null
            )
        )
    }

    private fun initHandlers() {
        infoButton.setOnClickListener {
            presenter.onInfoButtonClicked()
        }

        swipeContainer.setOnRefreshListener { presenter.onRefresh() }

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    presenter.onSearchQuerySubmit(query)
                    hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = true
            }
        )

        clearButton.setOnClickListener {
            presenter.onClearClicked()
        }
    }

    override fun setFeed(cafeList: List<CafeItem>) {
        initAdapter(cafeList)
        enableSwipeRefresh()
    }

    private fun initAdapter(cafeList: List<CafeItem>) {
        adapter = FeedAdapter(requireContext())
        adapter?.cafeList = cafeList

        cafeListRecycler.isVisible = true
        cafeListRecycler.adapter = adapter
    }

    private fun disableSwipeRefresh() {
        swipeContainer.isEnabled = false
        swipeContainer.isRefreshing = false
    }

    private fun enableSwipeRefresh() {
        swipeContainer.isEnabled = true
    }

    override fun showProgress() {
        disableSwipeRefresh()
        cafeListRecycler.isVisible = false
        progressBar.isVisible = true
    }

    override fun hideProgress() {
        progressBar.isVisible = false
        cafeListRecycler.isVisible = true
    }

    override fun showEmptySearchResult() {
        emptyResultView.isVisible = true
    }

    override fun hideEmptySearchResult() {
        emptyResultView.isVisible = false
    }

    override fun clearSearchQuery() {
        searchView.setQuery("", false)
        searchView.clearFocus()
    }
}