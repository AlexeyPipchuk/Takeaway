package takeaway.feature.basket.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import kotlinx.android.synthetic.main.basket_fragment.*
import takeaway.app.BaseFragment
import takeaway.app.loadImage
import takeaway.feature.basket.model.BasketItem
import takeaway.feature.basket.presentation.BasketPresenter
import takeaway.shared_cafe.domain.entity.Cafe
import javax.inject.Inject

class BasketFragment : BaseFragment(R.layout.basket_fragment), BasketView {

    companion object {
        fun getInstance(): Fragment = BasketFragment()
    }

    @Inject
    lateinit var presenter: BasketPresenter

    private var adapter: BasketAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
    }

    override fun showBasketItems(basketItems: List<BasketItem>) {
        adapter = BasketAdapter(
            context = requireContext(),
            onProductDeleteClickListener = presenter::onProductDeleteClicked
        )

        adapter?.basketItems = basketItems
        basketRecycler.adapter = adapter
    }

    private fun initListeners() {
        toolbar.setBackButtonListener {
            presenter.onBackButtonClick()
        }
        toOrderRegistrationButton.setOnClickListener {
            presenter.onToOrderRegistrationButtonClicked()
        }
    }

    override fun showOrderCafeInfo(cafe: Cafe) {
        orderCafeName.text = cafe.name

        val cafeLogoImg = cafe.logoUrl
        if (cafeLogoImg != null) {
            orderCafeLogo.isVisible = true
            orderCafeLogoImg.loadImage(cafeLogoImg)
        }
    }

    override fun showEmptyContent() {
        basketContent.isVisible = false
        emptyBasketText.isVisible = true
    }

    override fun setBasketAmount(basketAmount: Int) {
        orderAmountPrice.text = getString(R.string.rubles_postfix).format(basketAmount)
    }

    override fun showTakeawayDiscountCalculated(
        takeawayDiscount: Int,
        takeawayDiscountCalculated: Int
    ) {
        takeawayDiscountCalculatedText.isVisible = true
        takeawayDiscountCalculatedText.text = getString(
            R.string.takeaway_discount_calculated,
            takeawayDiscount.toString(),
            takeawayDiscountCalculated.toString()
        )
    }

    override fun showMinDeliverySum(minDeliverySum: Int) {
        minimumAmountForFreeDeliveryMessage.isVisible = true
        minimumAmountForFreeDeliveryMessage.text =
            getString(R.string.minimum_amount_for_free_delivery_message).format(minDeliverySum.toString())
    }

    override fun showDeliveryPriceCalculated(deliveryPriceCalculated: Int) {
        deliveryPriceCalculatedText.isVisible = true
        deliveryPriceCalculatedText.text =
            getString(R.string.delivery_price_calculated_text).format(deliveryPriceCalculated.toString())
    }

    override fun showMessageForFreeDelivery(valueToFreeDelivery: Int) {
        messageForFreeDelivery.isVisible = true
        messageForFreeDelivery.text =
            getString(R.string.message_with_value_to_free_delivery).format(valueToFreeDelivery.toString())
    }

    override fun setDefaultHelpMessagesState() {
        minimumAmountForFreeDeliveryMessage.isVisible = false
        deliveryPriceCalculatedText.isVisible = false
        messageForFreeDelivery.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}