package takeaway.feature.order.registration.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import kotlinx.android.synthetic.main.order_registration_appbar.view.*
import kotlinx.android.synthetic.main.order_registration_fragment.*
import takeaway.app.*
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.feature.order.registration.presentation.OrderRegistrationPresenter
import takeaway.feature.order.registration.presentation.OrderRegistrationView
import takeaway.shared.order.registration.domain.entity.OrderSketch
import javax.inject.Inject

private const val ORDER_SKETCH_ARG = "ORDER_SKETCH"
var Bundle.orderSketch: OrderSketch
    get() = getSerializable(ORDER_SKETCH_ARG) as OrderSketch
    set(value) = putSerializable(ORDER_SKETCH_ARG, value)

class OrderRegistrationFragment : BaseFragment(R.layout.order_registration_fragment),
    OrderRegistrationView {

    companion object {
        fun getInstance(orderSketch: OrderSketch): Fragment = OrderRegistrationFragment()
            .apply {
                arguments = Bundle().apply {
                    this.orderSketch = orderSketch
                }
            }
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
        takeawayRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.onTakeawayRadioButtonClicked()
                deliveryRadioButton.isChecked = false
            }
        }
        deliveryRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.onDeliveryRadioButtonClicked()
                takeawayRadioButton.isChecked = false
            }
        }
    }

    override fun setPrivacyPolicyText() {
        privacyPolicyLink.text = getString(R.string.privacy_policy_link_text).fromHtml()
    }

    override fun showOrderCafeInfo(cafe: Cafe) {
        orderCafeName.text = cafe.name

        val cafeLogoImg = cafe.logoUrl
        if (cafeLogoImg != null) {
            orderCafeLogo.isVisible = true
            orderCafeLogoImg.loadImage(cafeLogoImg)
        }
    }

    override fun showBasketAmount(basketAmountWithoutAll: Int) {
        orderAmountPrice.text =
            getString(R.string.rubles_postfix).format(basketAmountWithoutAll.toString())
    }

    override fun showTakeawayRadioButtonSubtitle(takeawayDiscount: Int) {
        takeawayRadioButtonSubtitle.text =
            getString(R.string.takeaway_radio_button_subtitle).format(takeawayDiscount.toString())
    }

    override fun showDeliveryValueToFreeDescription(valueToFreeDelivery: Int) {
        deliveryRadioButtonSubtitle.text =
            getString(R.string.message_with_value_to_free_delivery).format(valueToFreeDelivery.toString())
    }

    override fun showDeliveryMinSumDescription(deliveryMinSum: Int) {
        deliveryRadioButtonSubtitle.text =
            getString(R.string.minimum_amount_for_free_delivery_message).format(deliveryMinSum.toString())
    }

    override fun setTakeawayDiscountResult(takeawayDiscount: Int, takeawayDiscountCalculated: Int) {
        takeawayDiscountCalculatedText.text =
            getString(R.string.order_registration_takeaway_discount_text).format(takeawayDiscount.toString())

        takeawayDiscountCalculatedAmount.text =
            getString(R.string.rubles_postfix).format(takeawayDiscountCalculated.toString())
    }

    override fun showTakeawayDiscountResult() {
        takeawayOrderLayout.isVisible = true
    }

    override fun hideTakeawayDiscountResult() {
        takeawayOrderLayout.isVisible = false
    }

    override fun setDeliveryCostResult(takeawayDeliveryCalculated: Int) {
        deliveryPriceCalculatedAmount.text =
            getString(R.string.rubles_postfix).format(takeawayDeliveryCalculated.toString())
    }

    override fun showDeliveryCostResult() {
        deliveryOrderLayout.isVisible = true
    }

    override fun hideDeliveryCostResult() {
        deliveryOrderLayout.isVisible = false
    }

    override fun showOrderAmountWithTakeaway(orderWithTakeawayAmount: Int) {
        orderResultAmountPrice.text =
            getString(R.string.rubles_postfix).format(orderWithTakeawayAmount.toString())
    }

    override fun showOrderAmountWithDelivery(orderWithDeliveryAmount: Int) {
        orderResultAmountPrice.text =
            getString(R.string.rubles_postfix).format(orderWithDeliveryAmount.toString())
    }

    override fun disableResultAndButton() {
        orderResultAmountTitle.isVisible = false
        orderResultAmountPrice.isVisible = false
        orderSeparator.isVisible = false
        createOrderButton.disable()
    }

    override fun enableResultAndButton() {
        orderResultAmountTitle.isVisible = true
        orderResultAmountPrice.isVisible = true
        orderSeparator.isVisible = true
        createOrderButton.enable()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }
}