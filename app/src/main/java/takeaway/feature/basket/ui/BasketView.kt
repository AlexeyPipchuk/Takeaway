package takeaway.feature.basket.ui

import base.TakeawayView
import takeaway.feature.basket.model.BasketItem
import takeaway.shared_cafe.domain.entity.Cafe

interface BasketView : TakeawayView {

    fun showOrderCafeInfo(cafe: Cafe)

    fun showEmptyContent()

    fun showBasketItems(basketItems: List<BasketItem>)

    fun setBasketAmount(basketAmount: Int)

    fun showTakeawayDiscountCalculated(takeawayDiscount: Int, takeawayDiscountCalculated: Int)

    fun showMinDeliverySum(minDeliverySum: Int)

    fun showDeliveryPriceCalculated(deliveryPriceCalculated: Int)

    fun showMessageForFreeDelivery(valueToFreeDelivery: Int)

    fun setDefaultHelpMessagesState()
}