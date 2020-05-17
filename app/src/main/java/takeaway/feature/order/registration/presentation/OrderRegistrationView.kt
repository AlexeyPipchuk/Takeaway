package takeaway.feature.order.registration.presentation

import takeaway.app.TakeawayView
import takeaway.feature.feed.domain.entity.Cafe

interface OrderRegistrationView : TakeawayView {

    fun setPrivacyPolicyText()

    fun showOrderCafeInfo(cafe: Cafe)

    fun selectTakeawayReceivingMethod(adressList: List<String>?)

    fun selectDeliveryReceivingMethod()

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

    fun clearFocus()

    fun requestFocusOnFirstError()

    //Validation callbacks

    fun setNameValidationResult(error: String?)

    fun setPhoneValidationResult(error: String?)

    fun setEmailValidationResult(error: String?)

    fun setTakeawayAddressValidationResult(error: String?)

    fun setTakeawayTimeValidationResult(error: String?)

    fun setStreetValidationResult(error: String?)

    fun setHouseNumberValidationResult(error: String?)

    fun setPorchValidationResult(error: String?)

    fun setFloorValidationResult(error: String?)

    fun setFlatValidationResult(error: String?)

    fun setCommentValidationResult(error: String?)

    fun setDeliveryTimeValidationResult(error: String?)
}