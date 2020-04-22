package takeaway.feature.info.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import takeaway.app.BaseFragment
import takeaway.feature.info.presentation.InfoPresenter
import javax.inject.Inject

class InfoFragment : BaseFragment(R.layout.info_fragment), InfoView {

    companion object {
        fun getInstance(): Fragment = InfoFragment()
    }

    @Inject
    lateinit var presenter: InfoPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initToolbar()
    }

    private fun initToolbar() {
        initToolbar(R.string.info_title) {
            toolbar?.setNavigationOnClickListener {
                presenter.onBackClicked()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        //TODO(Внести в базу каким нибудь образом)
        presenter.detachView()
    }
}