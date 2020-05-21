package takeaway.feature.confirmation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import kotlinx.android.synthetic.main.confirmation_fragment.*
import takeaway.app.BaseFragment
import takeaway.app.addBackPressedListener
import takeaway.app.fromHtml
import takeaway.feature.confirmation.presentation.ConfirmationPresenter
import takeaway.feature.confirmation.presentation.ConfirmationView
import javax.inject.Inject

private const val ORDER_ID_ARG = "ORDER_ID"
var Bundle.orderId: String
    get() = getSerializable(ORDER_ID_ARG) as String
    set(value) = putSerializable(ORDER_ID_ARG, value)

class ConfirmationFragment : BaseFragment(R.layout.confirmation_fragment), ConfirmationView {

    companion object {
        fun getInstance(orderId: String): Fragment = ConfirmationFragment()
            .apply {
                arguments = Bundle().apply {
                    this.orderId = orderId
                }
            }
    }

    @Inject
    lateinit var presenter: ConfirmationPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
    }

    private fun initListeners() {
        toMainPageBackButton.setOnClickListener {
            presenter.onToMainPageBackClicked()
        }

        addBackPressedListener {
            presenter.onBackClicked()
        }
    }

    override fun setOrderId(orderId: String) {
        orderIdText.text = getString(R.string.confirmation_order_id_text)
            .format(orderId)
            .fromHtml()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }
}