package takeaway.feature.cafe.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import kotlinx.android.synthetic.main.cafe_fragment.*
import takeaway.app.BaseFragment
import takeaway.app.showNoInternetDialog
import takeaway.app.showServiceUnavailableDialog
import takeaway.feature.cafe.presentation.CafePresenter
import takeaway.feature.feed.domain.entity.Cafe
import javax.inject.Inject

private const val CAFE_ARG = "CAFE"
var Bundle.cafe: Cafe
    get() = getSerializable(CAFE_ARG) as Cafe
    set(value) = putSerializable(CAFE_ARG, value)

class CafeFragment : BaseFragment(R.layout.cafe_fragment), CafeView {

    companion object {
        fun getInstance(cafe: Cafe): Fragment = CafeFragment()
            .apply {
                arguments = Bundle().apply {
                    this.cafe = cafe
                }
            }
    }

    @Inject
    lateinit var presenter: CafePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun setCafeName(name: String) {
        cafeName.text = name
    }

    override fun onDestroyView() {
        super.onDestroyView()

        //TODO(Внести в базу каким нибудь образом)
        presenter.detachView()
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
}
