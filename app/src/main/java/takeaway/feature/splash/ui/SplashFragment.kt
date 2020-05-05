package takeaway.feature.splash.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import takeaway.app.BaseFragment
import takeaway.feature.splash.presentation.SplashPresenter
import takeaway.feature.splash.presentation.SplashView
import javax.inject.Inject

class SplashFragment : BaseFragment(R.layout.splash_fragment),
    SplashView {

    companion object {
        fun getInstance(): Fragment = SplashFragment()
    }

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun showStatusBar() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun hideStatusBar() {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}