package takeaway.feature.order.registration.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import kotlinx.android.synthetic.main.order_registration_appbar.view.*
import kotlinx.android.synthetic.main.order_registration_fragment.*
import takeaway.app.BaseFragment
import takeaway.app.fromHtml
import takeaway.feature.order.registration.presentation.OrderRegistrationPresenter
import takeaway.feature.order.registration.presentation.OrderRegistrationView
import javax.inject.Inject

class OrderRegistrationFragment : BaseFragment(R.layout.order_registration_fragment),
    OrderRegistrationView {

    companion object {
        fun getInstance(): Fragment = OrderRegistrationFragment()
    }

    @Inject
    lateinit var presenter: OrderRegistrationPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
    }

    private fun initListeners() {
        orderRegistrationAppbar.backToBasketButton.setOnClickListener {
            presenter.onBackToBasketButtonClicked()
        }
        createOrderButton.setOnClickListener {
            presenter.onCreateOrderButtonClicked()
        }
        privacyPolicyLink.setOnClickListener {
            presenter.onPrivacyPolicyClicked()
        }
    }

    override fun setPrivacyPolicyText() {
        privacyPolicyLink.text = getString(R.string.privacy_policy_link_text).fromHtml()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }
}