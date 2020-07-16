package takeaway.feature_success.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import base.BaseFragment
import extensions.addBackPressedListener
import kotlinx.android.synthetic.main.success_fragment.*
import takeaway.component_ui.extensions.fromHtml
import takeaway.feature_success.R
import takeaway.feature_success.presentation.SuccessPresenter
import takeaway.feature_success.presentation.SuccessView
import javax.inject.Inject

private const val ORDER_ID_ARG = "ORDER_ID"
var Bundle.orderId: String
    get() = getSerializable(ORDER_ID_ARG) as String
    set(value) = putSerializable(ORDER_ID_ARG, value)

class SuccessFragment : BaseFragment(R.layout.success_fragment), SuccessView {

    companion object {
        fun getInstance(orderId: String): Fragment = SuccessFragment()
            .apply {
                arguments = Bundle().apply {
                    this.orderId = orderId
                }
            }
    }

    @Inject
    lateinit var presenter: SuccessPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
    }

    private fun initListeners() {
        addBackPressedListener {
            presenter.onBackClicked()
        }

        toFeedButton.setOnClickListener {
            presenter.onFeedButtonClicked()
        }
    }

    override fun setOrderId(orderId: String) {
        orderIdText.text = getString(R.string.success_order_id_text)
            .format(orderId)
            .fromHtml()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}