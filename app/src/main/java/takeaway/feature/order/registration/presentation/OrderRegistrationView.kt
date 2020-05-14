package takeaway.feature.order.registration.presentation

import takeaway.app.TakeawayView
import takeaway.feature.feed.domain.entity.Cafe

interface OrderRegistrationView : TakeawayView {

    fun setPrivacyPolicyText()

    fun showOrderCafeInfo(cafe: Cafe)

    fun showBasketAmount(basketAmountWithoutAll: Int)

    fun showTakeawayRadioButtonSubtitle(takeawayDiscount: Int)

    fun showDeliveryValueToFreeDescription(valueToFreeDelivery: Int)

    fun showDeliveryMinSumDescription(deliveryMinSum: Int)

    fun setTakeawayDiscountResult(
        takeawayDiscount: Int,
        takeawayDiscountCalculated: Int
    )

    fun showTakeawayDiscountResult()

    fun hideTakeawayDiscountResult()

    fun setDeliveryCostResult(takeawayDeliveryCalculated: Int)

    fun showDeliveryCostResult()

    fun hideDeliveryCostResult()

    fun showOrderAmountWithTakeaway(orderWithTakeawayAmount: Int)

    fun showOrderAmountWithDelivery(orderWithDeliveryAmount: Int)

    fun disableResultAndButton()

    fun enableResultAndButton()
}