package takeaway.feature.order.registration.presentation

import domain.usecase.GetPhoneCountryPrefixUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router
import base.BasePresenter
import takeaway.shared_error.ErrorConverter
import takeaway.shared_error.ErrorType
import takeaway.app.navigation.Screen
import takeaway.feature.order.registration.domain.entity.Order
import takeaway.feature.order.registration.domain.entity.OrderValidatorField
import takeaway.feature.order.registration.domain.entity.ReceiveMethod
import takeaway.feature.order.registration.domain.usecase.CreateOrderUseCase
import takeaway.feature.order.registration.domain.usecase.GetIntervalListUseCase
import takeaway.feature.order.registration.domain.usecase.validation.*
import takeaway.shared.basket.domian.usecase.ClearBasketUseCase
import javax.inject.Inject

class OrderRegistrationPresenter @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateCommonStringUseCase: ValidateCommonStringUseCase,
    private val validateSingleNumberUseCase: ValidateSingleNumberUseCase,
    private val validateExportExportTimeUseCase: ValidateExportTimeUseCase,
    private val clearBasketUseCase: ClearBasketUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val getPhoneCountryPrefixUseCase: GetPhoneCountryPrefixUseCase,
    private val getIntervalListUseCase: GetIntervalListUseCase,
    private val errorConverter: ErrorConverter,
    private val router: Router,
    private val orderSketch: domain.entity.OrderSketch
) : BasePresenter<OrderRegistrationView>() {

    var order = Order.EMPTY.copy(cafe = orderSketch.cafe, productMap = orderSketch.products)

    override fun onViewAttach() {
        super.onViewAttach()

        view?.showOrderCafeInfo(orderSketch.cafe)
        view?.showBasketAmount(orderSketch.basketAmountWithoutAll)
        view?.setPrivacyPolicyText()
        view?.setPhoneMask(getPhoneCountryPrefixUseCase())

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
        val timeList = getAvailableExportTimeList()
        if (timeList.isEmpty()) {
            view?.showEmptyExportTimeListError()
        } else {
            view?.showPopUpWithAvailableTakeawayTimes(getAvailableExportTimeList())
        }
    }

    fun onTimeDeliveryClicked() {
        val timeList = getAvailableExportTimeList()
        if (timeList.isEmpty()) {
            view?.showEmptyExportTimeListError()
        } else {
            view?.showPopUpWithAvailableDeliveryTimes(getAvailableExportTimeList())
        }
    }

    private fun getAvailableExportTimeList(): List<String> =
        getIntervalListUseCase(orderSketch.cafe.workFrom, orderSketch.cafe.workTo)

    fun onAddressSelected(address: String) {
        order = order.copy(takeawayAddress = address)
        view?.setAddress(address)
    }

    fun onTakeawayTimeSelected(takeawayTime: String) {
        order = order.copy(takeawayTime = takeawayTime)
        view?.setTakeawayTime(takeawayTime)
    }

    fun onDeliveryTimeSelected(deliveryTime: String) {
        order = order.copy(deliveryTime = deliveryTime)
        view?.setDeliveryTime(deliveryTime)
    }

    fun onTakeawayRadioButtonClicked() {
        order = order.copy(receiveMethod = ReceiveMethod.TAKEAWAY)

        showInputFieldsForTakeaway()
        showOrderAmountWithTakeaway()
    }

    fun onDeliveryRadioButtonClicked() {
        order = order.copy(receiveMethod = ReceiveMethod.DELIVERY)

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

        val isNameValid = validateName()
        val isPhoneValid = validatePhone()
        val isEmailValid = validateEmail()
        val isTakeawayTimeValid = validateTakeawayTime()

        if (
            isNameValid &&
            isPhoneValid &&
            isEmailValid &&
            isTakeawayTimeValid
        ) {
            onOrderValid()
        } else view?.requestFocusOnFirstError()
    }

    private fun validateDeliveryOrder(onOrderValid: () -> Unit) {
        view?.clearFocus()

        val isNameValid = validateName()
        val isPhoneValid = validatePhone()
        val isEmailValid = validateEmail()
        val isDeliveryTimeValid = validateDeliveryTime()
        val isStreetValid = validateStreet()
        val isHouseNumberValid = validateHouseNumber()
        val isPorchValid = validatePorch()
        val isFloorValid = validateFloor()
        val isFlatValid = validateFlat()
        val isCommentValid = validateComment()

        if (
            isNameValid &&
            isPhoneValid &&
            isEmailValid &&
            isDeliveryTimeValid &&
            isStreetValid &&
            isHouseNumberValid &&
            isPorchValid &&
            isFloorValid &&
            isFlatValid &&
            isCommentValid
        ) {
            onOrderValid()
        } else view?.requestFocusOnFirstError()
    }

    private fun createOrder() {
        view?.showProgress()

        createOrderUseCase(order)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { orderId ->
                    view?.hideProgress()
                    clearBasketUseCase()
                    router.navigateTo(Screen.ConfirmationScreen(orderId))
                },
                {
                    view?.hideProgress()
                    handleError(it)
                }
            )
            .addToDisposable()
    }

    private fun handleError(error: Throwable) {
        when (errorConverter.convert(error)) {
            ErrorType.BAD_INTERNET -> view?.showNoInternetDialog()
            ErrorType.SERVICE_UNAVAILABLE -> view?.showServiceUnavailable()
        }
    }

    fun onNegativeButtonClicked() {
        router.newRootScreen(Screen.FeedScreen(noInternet = true))
    }

    fun onRetryClicked() {
        createOrder()
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

            OrderValidatorField.STREET -> {
                order = order.copy(street = value)
                validateStreet()
            }

            OrderValidatorField.HOUSE_NUMBER -> {
                order = order.copy(houseNumber = value)
                validateHouseNumber()
            }

            OrderValidatorField.PORCH -> {
                order = order.copy(porch = value)
                validatePorch()
            }

            OrderValidatorField.FLOOR -> {
                order = order.copy(floor = value)
                validateFloor()
            }

            OrderValidatorField.FLAT -> {
                order = order.copy(flat = value)
                validateFlat()
            }

            OrderValidatorField.COMMENT -> {
                order = order.copy(comment = value)
                validateComment()
            }
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

    private fun validateStreet(): Boolean {
        val validationResult = validateCommonStringUseCase(order.street, required = true)
        view?.setStreetValidationResult(validationResult)

        return validationResult == null
    }

    private fun validateHouseNumber(): Boolean {
        val validationResult = validateSingleNumberUseCase(order.houseNumber, required = true)
        view?.setHouseNumberValidationResult(validationResult)

        return validationResult == null
    }

    private fun validatePorch(): Boolean {
        val validationResult = validateSingleNumberUseCase(order.porch, required = false)
        view?.setPorchValidationResult(validationResult)

        return validationResult == null
    }

    private fun validateFloor(): Boolean {
        val validationResult = validateSingleNumberUseCase(order.floor, required = false)
        view?.setFloorValidationResult(validationResult)

        return validationResult == null
    }

    private fun validateFlat(): Boolean {
        val validationResult = validateSingleNumberUseCase(order.flat, required = true)
        view?.setFlatValidationResult(validationResult)

        return validationResult == null
    }

    private fun validateComment(): Boolean {
        val validationResult = validateCommonStringUseCase(order.comment, required = false)
        view?.setCommentValidationResult(validationResult)

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