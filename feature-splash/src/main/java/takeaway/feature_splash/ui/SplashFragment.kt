package takeaway.feature_splash.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import base.BaseFragment
import kotlinx.android.synthetic.main.splash_fragment.*
import takeaway.feature_splash.R
import takeaway.feature_splash.presentation.SplashPresenter
import takeaway.feature_splash.presentation.SplashView
import javax.inject.Inject

private const val DEEP_LINK_ARG = "DEEP_LINK_ARG"
var Bundle.deepLink: Uri?
    get() = getParcelable(DEEP_LINK_ARG)
    set(value) = putParcelable(DEEP_LINK_ARG, value)

class SplashFragment : BaseFragment(R.layout.splash_fragment), SplashView {

    private val windowInsetListener = View.OnApplyWindowInsetsListener { windowView, insets ->
        splashView?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = insets.systemWindowInsetBottom
            bottomMargin = insets.systemWindowInsetTop
        }
        windowView.onApplyWindowInsets(insets)
    }

    companion object {
        fun getInstance(deepLink: Uri? = null): Fragment = SplashFragment().apply {
            arguments = Bundle().apply {
                this.deepLink = deepLink
            }
        }
    }

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun showStatusBar() {
        activity?.window?.decorView?.setOnApplyWindowInsetsListener(null)
    }

    override fun hideStatusBar() {
        activity?.window?.decorView?.setOnApplyWindowInsetsListener(windowInsetListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}