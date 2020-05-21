package takeaway.feature.order.registration.presentation

import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.getIntervalListBetween
import takeaway.app.navigation.Screen
import takeaway.feature.order.registration.domain.entity.Order
import takeaway.feature.order.registration.domain.entity.OrderValidatorField
import takeaway.feature.order.registration.domain.entity.ReceiveMethod
import takeaway.feature.order.registration.domain.usecase.CreateOrderUseCase
import takeaway.feature.order.registration.domain.usecase.validation.*
import takeaway.shared.order.registration.domain.entity.OrderSketch
import javax.inject.Inject

class OrderRegistrationPresenter @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateCommonStringUseCase: ValidateCommonStringUseCase,
    private val validateSingleNumberUseCase: ValidateSingleNumberUseCase,
    private val validateExportExportTimeUseCase: ValidateExportTimeUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val orderSketch: OrderSketch,
    private val router: Router
) : BasePresenter<OrderRegistrationView>() {

    var order = Order.EMPTY.copy(cafe = orderSketch.cafe, productMap = orderSketch.products)

    override fun onViewAttach() {
        super.onViewAttach()

        view?.showOrderCafeInfo(orderSketch.cafe)
        view?.showBasketAmount(orderSketch.basketAmountWithoutAll)
        view?.setPrivacyPolicyText()

        setUpSubtitles()

        setUpTakeawayAndDeliveryCalculatedValues()

        showInputFieldsForTakeaway()
        showOrderAmountWithTakeaway()
    }

    private fun setUpSubtitles() {
        if (orderSketch.cafe.takeawayDiscount != 0) {
            view?.showTakeawayRadioButtonSubtitle(orderSketch.cafe.takeawayDiscount)
        }

        when {
            orderSketch.deliveryCostCalculated == 0 && orderSketch.deliveryAllowed -> Unit
            orderSketch.deliveryAllowed -> view?.showDeliveryValueToFreeDescription(orderSketch.valueToFreeDelivery)
            else -> view?.showDeliveryMinSumDescription(orderSketch.cafe.minDeliverySum)
        }
    }

    private fun setUpTakeawayAndDeliveryCalculatedValues() {
        view?.setTakeawayDiscountResult(
            orderSketch.cafe.takeawayDiscount,
            orderSketch.takeawayDiscountCalculated
        )

        view?.setDeliveryCostResult(
            orderSketch.deliveryCostCalculated
        )
    }

    private fun showInputFieldsForTakeaway() {
        val severalAddresses = listOf(orderSketch.cafe.address).count() > 1
        view?.selectTakeawayReceivingMethod(severalAddresses)
    }

    private fun showOrderAmountWithTakeaway() {
        view?.showOrderAmountWithTakeaway(orderSketch.orderWithTakeawayAmount)
        if (orderSketch.cafe.takeawayDiscount != 0) {
            view?.showTakeawayDiscountResult()
        }
        view?.hideDeliveryCostResult()
        view?.enableResultAndButton()
    }

    private fun showOrderAmountWithDelivery() {
        if (orderSketch.deliveryAllowed) {
            view?.showOrderAmountWithDelivery(orderSketch.orderWithDeliveryAmount)
            view?.showDeliveryCostResult()
            view?.hideTakeawayDiscountResult()
        } else {
            view?.hideTakeawayDiscountResult()
            view?.hideDeliveryCostResult()
            view?.disableResultAndButton()
        }
    }

    fun onBackToBasketButtonClicked() {
        router.exit()
    }

    fun onAddressTakeawayClicked() {
        //TODO(Это задел для поддержки нескольких адресов, не реализовано сразу т.к. бэк присылает не список)
        view?.showPopUpWithAddresses(listOf(orderSketch.cafe.address))
    }

    fun onTimeTakeawayClicked() {
        view?.showPopUpWithAvailableTakeawayTimes(getAvailableExportTimeList())
    }

    fun onTimeDeliveryClicked() {
        view?.showPopUpWithAvailableDeliveryTimes(getAvailableExportTimeList())
    }

    private fun getAvailableExportTimeList(): List<String> =
        orderSketch.cafe.workFrom.getIntervalListBetween(orderSketch.cafe.workTo)

    fun onAddressSelected(address: String) {
        view?.setAddress(address)
    }

    fun onTakeawayTimeSelected(takeawayTime: String) {
        view?.setTakeawayTime(takeawayTime)
    }

    fun onDeliveryTimeSelected(deliveryTime: String) {
        view?.setDeliveryTime(deliveryTime)
    }

    fun onTakeawayRadioButtonClicked() {
        showInputFieldsForTakeaway()
        showOrderAmountWithTakeaway()
    }

    fun onDeliveryRadioButtonClicked() {
        view?.selectDeliveryReceivingMethod()
        showOrderAmountWithDelivery()
    }

    fun onCreateOrderButtonClicked() {
        if (order.receiveMethod == ReceiveMethod.TAKEAWAY) {
            validateTakeawayOrder(::createOrder)
        } else {
            validateDeliveryOrder(::createOrder)
        }
    }

    private fun validateTakeawayOrder(onOrderValid: () -> Unit) {
        view?.clearFocus()

        if (
            validateName() &&
            validatePhone() &&
            validateEmail() &&
            validateTakeawayTime()
        ) {
            onOrderValid()
        } else view?.requestFocusOnFirstError()
    }

    private fun validateDeliveryOrder(onOrderValid: () -> Unit) {
        view?.clearFocus()

        if (
            validateName() &&
            validatePhone() &&
            validateEmail() &&
            validateDeliveryTime()
        ) {
            onOrderValid()
        } else view?.requestFocusOnFirstError()
    }

    private fun createOrder() {
        createOrderUseCase(order)
    }

    fun onInputFieldFocusLost(value: String, field: OrderValidatorField) {
        when (field) {

            OrderValidatorField.NAME -> {
                order = order.copy(name = value)
                validateName()
            }

            OrderValidatorField.PHONE -> {
                order = order.copy(phone = value)
                validatePhone()
            }

            OrderValidatorField.EMAIL -> {
                order = order.copy(email = value)
                validateEmail()
            }
            else -> Unit
//
//            OrderValidatorField.STREET -> {
//                view?.setStreetValidationResult(
//                    validateCommonStringUseCase(order.street, required = true)
//                )
//            }
//            OrderValidatorField.HOUSE_NUMBER -> {
//                view?.setHouseNumberValidationResult(
//                    validateSingleNumberUseCase(order.houseNumber, required = true)
//                )
//            }
//
//            OrderValidatorField.PORCH -> {
//                view?.setPorchValidationResult(
//                    validateSingleNumberUseCase(order.porch, required = false)
//                )
//            }
//
//            OrderValidatorField.FLOOR -> {
//                view?.setFloorValidationResult(
//                    validateSingleNumberUseCase(order.floor, required = false)
//                )
//            }
//
//            OrderValidatorField.FLAT -> {
//                view?.setFlatValidationResult(
//                    validateSingleNumberUseCase(order.flat, required = true)
//                )
//            }
//            OrderValidatorField.COMMENT -> {
//                view?.setCommentValidationResult(
//                    validateCommonStringUseCase(order.comment, required = false)
//                )
//            }
//
        }
    }

    private fun validateName(): Boolean {
        val validationResult = validateNameUseCase(order.name)
        view?.setNameValidationResult(validationResult)

        return validationResult == null
    }

    private fun validatePhone(): Boolean {
        val validationResult = validatePhoneUseCase(order.phone)
        view?.setPhoneValidationResult(validationResult)

        return validationResult == null
    }

    private fun validateEmail(): Boolean {
        val validationResult = validateEmailUseCase(order.email)
        view?.setEmailValidationResult(validationResult)

        return validationResult == null
    }

    private fun validateTakeawayTime(): Boolean {
        val validationResult =
            validateExportExportTimeUseCase(order.takeawayTime, getAvailableExportTimeList())
        view?.setTakeawayTimeValidationResult(validationResult)

        return validationResult == null
    }

    private fun validateDeliveryTime(): Boolean {
        val validationResult =
            validateExportExportTimeUseCase(order.deliveryTime, getAvailableExportTimeList())
        view?.setDeliveryTimeValidationResult(validationResult)

        return validationResult == null
    }

    fun onPrivacyPolicyClicked() {
        router.navigateTo(Screen.PrivacyPolicyScreen)
    }
}