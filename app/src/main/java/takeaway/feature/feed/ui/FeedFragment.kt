package takeaway.feature.feed.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import kotlinx.android.synthetic.main.empty_search_result_view.*
import kotlinx.android.synthetic.main.fab_layout.view.*
import kotlinx.android.synthetic.main.feed_fragment.*
import takeaway.app.BaseFragment
import takeaway.app.hideKeyboard
import takeaway.app.showNoInternetDialog
import takeaway.app.showServiceUnavailableDialog
import takeaway.feature.feed.presentation.FeedPresenter
import takeaway.feature.feed.presentation.model.FeedItem
import takeaway.feature.feed.promo.ui.PromoDialogFragment
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
        fabMenuButton.setOnClickListener {
            presenter.onFabMenuButtonClicked()
        }

        val infoButtonClickListener = View.OnClickListener {
            presenter.onInfoButtonClicked()
        }
        fabMenu.infoButtonLayout.setOnClickListener(infoButtonClickListener)
        fabMenu.infoButton.setOnClickListener(infoButtonClickListener)

        val addCafeButtonClickListener = View.OnClickListener {
            presenter.onAddCafeButtonClicked()
        }
        fabMenu.addCafeButtonLayout.setOnClickListener(addCafeButtonClickListener)
        fabMenu.addCafeButton.setOnClickListener(addCafeButtonClickListener)

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

    override fun setFeed(cafeItemList: List<FeedItem>) {
        initAdapter(cafeItemList)
        enableSwipeRefresh()
    }

    private fun initAdapter(cafeItemList: List<FeedItem>) {
        adapter = FeedAdapter(
            context = requireContext(),
            onCafeClickListener = presenter::onCafeClicked,
            onPromoClickListener = presenter::onPromoClicked
        )
        adapter?.feedItems = cafeItemList

        feedItemListRecycler.isVisible = true
        feedItemListRecycler.adapter = adapter
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
        feedItemListRecycler.isVisible = false
        progressBar.isVisible = true
    }

    override fun hideProgress() {
        progressBar.isVisible = false
        feedItemListRecycler.isVisible = true
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

    override fun showNoInternetDialog() {
        showNoInternetDialog(
            positiveResult = {
                presenter.onRetryClicked()
            }, negativeResult = {
                //TODO(Заглушка с возможностью повторить)
            }
        )
    }

    override fun showServiceUnavailable() {
        showServiceUnavailableDialog(
            positiveResult = {
                presenter.onRetryClicked()
            }, negativeResult = {
                //TODO(Заглушка с возможностью повторить)
            }
        )
    }

    override fun showPromoDialog() {
        val promoDialog = PromoDialogFragment()
        promoDialog.setTargetFragment(this, 0)
        fragmentManager?.let { fragmentManager ->
            promoDialog.show(
                fragmentManager,
                promoDialog::class.java.name
            )
        }
    }

    override fun openFabMenu() {
        val newInfoButtonMarginBottom = (fabMenu.infoButtonLayout.height * 1.75).toInt()
        val fab1Animation = AnimationUtils.loadAnimation(context, R.anim.fab1_show)
        fabMenu.infoButtonLayout.startAnimation(fab1Animation)
        fabMenu.infoButtonLayout.isClickable = true
        fabMenu.infoButtonLayout.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = newInfoButtonMarginBottom
        }

        val addCafeButtonNewMarginBottom = (fabMenu.addCafeButtonLayout.height * 2.75).toInt()
        val fab2Animation = AnimationUtils.loadAnimation(context, R.anim.fab2_show)
        fabMenu.addCafeButtonLayout.startAnimation(fab2Animation)
        fabMenu.addCafeButtonLayout.isClickable = true
        fabMenu.addCafeButtonLayout.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = addCafeButtonNewMarginBottom
        }
    }

    override fun closeFabMenu() {
        val newInfoButtonMarginBottom = (fabMenu.infoButtonLayout.height * 1.75).toInt()
        val fab1Animation = AnimationUtils.loadAnimation(context, R.anim.fab1_hide)
        fabMenu.infoButtonLayout.startAnimation(fab1Animation)
        fabMenu.infoButtonLayout.isClickable = false
        fabMenu.infoButtonLayout.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin -= newInfoButtonMarginBottom
        }

        val addCafeButtonNewMarginBottom = (fabMenu.addCafeButtonLayout.height * 2.75).toInt()
        val fab2Animation = AnimationUtils.loadAnimation(context, R.anim.fab2_hide)
        fabMenu.addCafeButtonLayout.startAnimation(fab2Animation)
        fabMenu.addCafeButtonLayout.isClickable = false
        fabMenu.addCafeButtonLayout.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin -= addCafeButtonNewMarginBottom
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        //TODO(Внести в базу каким нибудь образом)
        presenter.detachView()
    }
}