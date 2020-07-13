package takeaway.shared_error.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import base.BaseFragment
import extensions.addBackPressedListener
import kotlinx.android.synthetic.main.no_internet_fragment.*
import takeaway.shared_error.R
import takeaway.shared_error.presentation.NoInternetPresenter
import takeaway.shared_error.presentation.NoInternetView
import javax.inject.Inject

class NoInternetFragment : BaseFragment(R.layout.no_internet_fragment), NoInternetView {

    companion object {
        fun getInstance(): Fragment =
            NoInternetFragment()
    }

    @Inject
    lateinit var presenter: NoInternetPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initHandlers()
    }

    private fun initHandlers() {
        retryConnectButton.setOnClickListener {
            presenter.onRetryConnectClicked()
        }
        takeawayInfoButton.setOnClickListener {
            presenter.onTakeawayInfoClicked()
        }
        addBackPressedListener {
            presenter.onBackClicked()
        }
    }

    override fun showProgress() {
        content.isVisible = false
        progressBar.isVisible = true
    }

    override fun hideProgress() {
        progressBar.isVisible = false
        content.isVisible = true
    }

    override fun closeApp() {
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}