package takeaway.feature_order_registration.presentation

import com.nhaarman.mockitokotlin2.*
import domain.entity.OrderSketch
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.component_test.TestSchedulerRule
import takeaway.component_validation.domain.usecase.*
import takeaway.feature_order_registration.cafe
import takeaway.feature_order_registration.domain.entity.Order
import takeaway.feature_order_registration.domain.entity.OrderValidatorField
import takeaway.feature_order_registration.domain.entity.ReceiveMethod
import takeaway.feature_order_registration.domain.usecase.CreateOrderUseCase
import takeaway.feature_order_registration.domain.usecase.GetIntervalListUseCase
import takeaway.shared_basket.domain.usecase.ClearBasketUseCase
import takeaway.shared_basket.productMap
import takeaway.shared_error.ErrorConverter
import takeaway.shared_error.ErrorType
import takeaway.shared_phone_prefix.domain.usecase.GetPhoneCountryPrefixUseCase

@RunWith(MockitoJUnitRunner::class)
class OrderRegistrationPresenterTest {

    @Rule
    @JvmField
    val testRule = TestSchedulerRule()

    private val validateNameUseCase: ValidateNameUseCase = mock()
    private val validatePhoneUseCase: ValidatePhoneUseCase = mock()
    private val validateEmailUseCase: ValidateEmailUseCase = mock()
    private val validateCommonStringUseCase: ValidateCommonStringUseCase = mock()
    private val validateSingleNumberUseCase: ValidateSingleNumberUseCase = mock()
    private val validateExportTimeUseCase: ValidateExportTimeUseCase = mock()
    private val clearBasketUseCase: ClearBasketUseCase = mock()
    private val createOrderUseCase: CreateOrderUseCase = mock()
    private val getPhoneCountryPrefixUseCase: GetPhoneCountryPrefixUseCase = mock()
    private val getIntervalListUseCase: GetIntervalListUseCase = mock()
    private val errorConverter: ErrorConverter = mock()
    private val router: OrderRegistrationRouter = mock()
    private val orderSketch = OrderSketch(
        cafe = cafe,
        basketAmountWithoutAll = 100,
        products = productMap,
        takeawayDiscountCalculated = 50,
        orderWithTakeawayAmount = 150,
        deliveryCostCalculated = 50,
        orderWithDeliveryAmount = 100,
        valueToFreeDelivery = 50,
        deliveryAllowed = true
    )

    private val view: OrderRegistrationView = mock()

    private val presenter = OrderRegistrationPresenter(
        validateNameUseCase,
        validatePhoneUseCase,
        validateEmailUseCase,
        validateCommonStringUseCase,
        validateSingleNumberUseCase,
        validateExportTimeUseCase,
        clearBasketUseCase,
        createOrderUseCase,
        getPhoneCountryPrefixUseCase,
        getIntervalListUseCase,
        errorConverter,
        router,
        orderSketch
    )

    @Test
    fun `view attached EXPECT show order cafe info`() {
        presenter.attachView(view)

        verify(view).showOrderCafeInfo(orderSketch.cafe)
    }

    @Test
    fun `view attached EXPECT show basket amount`() {
        presenter.attachView(view)

        verify(view).showBasketAmount(orderSketch.basketAmountWithoutAll)
    }

    @Test
    fun `view attached EXPECT set privacy policy text`() {
        presenter.attachView(view)

        verify(view).setPrivacyPolicyText()
    }

    @Test
    fun `view attached EXPECT set phone mask`() {
        whenever(getPhoneCountryPrefixUseCase()).thenReturn("+7")

        presenter.attachView(view)

        verify(view).setPhoneMask(getPhoneCountryPrefixUseCase())
    }

    @Test
    fun `takeaway discount not 0 EXPECT show takeaway radio button subtitle`() {
        val orderSketch = orderSketch.copy(cafe = cafe.copy(takeawayDiscount = 100))
        val presenter = createPresenter(orderSketch)

        presenter.attachView(view)

        verify(view).showTakeawayRadioButtonSubtitle(orderSketch.cafe.takeawayDiscount)
    }

    @Test
    fun `delivery cost calculated is 0 and delivery allowed EXPECT do not show delivery min sum and value to free delivery`() {
        val orderSketch = orderSketch.copy(deliveryCostCalculated = 0, deliveryAllowed = true)
        val presenter = createPresenter(orderSketch)

        presenter.attachView(view)

        verify(view, never()).showDeliveryValueToFreeDescription(any())
        verify(view, never()).showDeliveryMinSumDescription(any())
    }

    @Test
    fun `delivery cost calculated is not 0 and delivery allowed EXPECT show value to free delivery`() {
        val orderSketch = orderSketch.copy(deliveryCostCalculated = 100, deliveryAllowed = true)
        val presenter = createPresenter(orderSketch)

        presenter.attachView(view)

        verify(view).showDeliveryValueToFreeDescription(orderSketch.valueToFreeDelivery)
    }

    @Test
    fun `delivery not allowed EXPECT show delivery min sum description`() {
        val orderSketch = orderSketch.copy(deliveryAllowed = false)
        val presenter = createPresenter(orderSketch)

        presenter.attachView(view)

        verify(view).showDeliveryMinSumDescription(orderSketch.cafe.minDeliverySum)
    }

    @Test
    fun `view attached EXPECT set takeaway discount result`() {
        presenter.attachView(view)

        verify(view).setTakeawayDiscountResult(
            orderSketch.cafe.takeawayDiscount,
            orderSketch.takeawayDiscountCalculated
        )
    }

    @Test
    fun `view attached EXPECT set delivery cost result`() {
        presenter.attachView(view)

        verify(view).setDeliveryCostResult(orderSketch.deliveryCostCalculated)
    }

    //TODO(Когда заедет несколько адресов)
//    @Test
//    fun `view attached and cafe have several addresses EXPECT select takeaway receiving method with true`() {
//        val orderSketch = orderSketch.copy(cafe = cafe.copy(address = listOf("address", "address2")))
//        val presenter = createPresenter(orderSketch)
//
//        presenter.attachView(view)
//
//        verify(view).selectTakeawayReceivingMethod(true)
//    }

    @Test
    fun `view attached and cafe have one address EXPECT select takeaway receiving method with false`() {
        val orderSketch = orderSketch.copy(cafe = cafe.copy(address = "address"))
        val presenter = createPresenter(orderSketch)

        presenter.attachView(view)

        verify(view).selectTakeawayReceivingMethod(false)
    }

    @Test
    fun `view attached EXPECT show order amount with takeaway`() {
        presenter.attachView(view)

        verify(view).showOrderAmountWithTakeaway(orderSketch.orderWithTakeawayAmount)
    }

    @Test
    fun `view attached and cafe takeaway discount is not 0 EXPECT show takeaway discount result`() {
        val orderSketch = orderSketch.copy(cafe = cafe.copy(takeawayDiscount = 100))
        val presenter = createPresenter(orderSketch)

        presenter.attachView(view)

        verify(view).showTakeawayDiscountResult()
    }

    @Test
    fun `view attached and cafe takeaway discount is 0 EXPECT do not show takeaway discount result`() {
        val orderSketch = orderSketch.copy(cafe = cafe.copy(takeawayDiscount = 0))
        val presenter = createPresenter(orderSketch)

        presenter.attachView(view)

        verify(view, never()).showTakeawayDiscountResult()
    }

    @Test
    fun `view attached EXPECT hide delivery cost result`() {
        presenter.attachView(view)

        verify(view).hideDeliveryCostResult()
    }

    @Test
    fun `view attached EXPECT enable result and button`() {
        presenter.attachView(view)

        verify(view).enableResultAndButton()
    }

    @Test
    fun `back to basket clicked EXPECT back to basket`() {
        presenter.onBackToBasketButtonClicked()

        verify(router).backToBasket()
    }

    @Test
    fun `on address takeaway clicked EXPECT show pop up with addresses`() {
        val address = "address"
        val orderSketch = orderSketch.copy(cafe = cafe.copy(address = address))
        val presenter = createPresenter(orderSketch)

        presenter.attachView(view)
        presenter.onAddressTakeawayClicked()

        verify(view).showPopUpWithAddresses(listOf(address))
    }

    @Test
    fun `time takeaway clicked and time list is empty EXPECT show empty export time list error`() {
        whenever(getIntervalListUseCase(any(), any())).thenReturn(emptyList())
        presenter.attachView(view)

        presenter.onTimeTakeawayClicked()

        verify(view).showEmptyExportTimeListError()
    }

    @Test
    fun `time takeaway clicked and time list is not empty EXPECT show pop up with available takeaway times`() {
        val timeList = listOf("10:00", "10:15")
        whenever(getIntervalListUseCase(any(), any())).thenReturn(timeList)
        presenter.attachView(view)

        presenter.onTimeTakeawayClicked()

        verify(view).showPopUpWithAvailableTakeawayTimes(timeList)
    }

    @Test
    fun `time delivery clicked and time list is empty EXPECT show empty export time list error`() {
        whenever(getIntervalListUseCase(any(), any())).thenReturn(emptyList())
        presenter.attachView(view)

        presenter.onTimeDeliveryClicked()

        verify(view).showEmptyExportTimeListError()
    }

    @Test
    fun `time delivery clicked and time list is not empty EXPECT show pop up with available delivery times`() {
        val timeList = listOf("10:00", "10:15")
        whenever(getIntervalListUseCase(any(), any())).thenReturn(timeList)
        presenter.attachView(view)

        presenter.onTimeDeliveryClicked()

        verify(view).showPopUpWithAvailableDeliveryTimes(timeList)
    }

    @Test
    fun `on address selected EXPECT set address`() {
        val address = "address"
        presenter.attachView(view)

        presenter.onAddressSelected(address)

        verify(view).setAddress(address)
    }

    @Test
    fun `on takeaway time selected EXPECT set takeaway time`() {
        val takeawayTime = "10:00"
        presenter.attachView(view)

        presenter.onTakeawayTimeSelected(takeawayTime)

        verify(view).setTakeawayTime(takeawayTime)
    }

    @Test
    fun `on delivery time selected EXPECT set delivery time`() {
        val deliveryTime = "10:00"
        presenter.attachView(view)

        presenter.onDeliveryTimeSelected(deliveryTime)

        verify(view).setDeliveryTime(deliveryTime)
    }

    //TODO("Раскоменить после подержки нескольких адресов")
//    @Test
//    fun `on takeaway radio button clicked and cafe have several address EXPECT show input fields for takeaway with true several addresses`() {
//        val addresses = listOf("address", "address2")
//        val orderSketch = orderSketch.copy(cafe = cafe.copy(address = addresses))
//        val presenter = createPresenter(orderSketch)
//        presenter.attachView(view)
//        clearInvocations(view)
//
//        presenter.onTakeawayRadioButtonClicked()
//
//        verify(view).selectTakeawayReceivingMethod(severalAddresses = true)
//    }

    @Test
    fun `on takeaway radio button clicked and cafe have one address EXPECT show input fields for takeaway with false several addresses`() {
        val orderSketch = orderSketch.copy(cafe = cafe.copy(address = "address"))
        val presenter = createPresenter(orderSketch)
        presenter.attachView(view)
        clearInvocations(view)

        presenter.onTakeawayRadioButtonClicked()

        verify(view).selectTakeawayReceivingMethod(severalAddresses = false)
    }

    @Test
    fun `on takeaway radio button clicked EXPECT show order amount with takeaway`() {
        presenter.attachView(view)

        verify(view).showOrderAmountWithTakeaway(orderSketch.orderWithTakeawayAmount)
    }

    @Test
    fun `on takeaway radio button clicked and cafe takeaway discount is not 0 EXPECT show order amount with takeaway`() {
        val orderSketch = orderSketch.copy(cafe = cafe.copy(takeawayDiscount = 100))
        val presenter = createPresenter(orderSketch)
        presenter.attachView(view)
        clearInvocations(view)

        presenter.onTakeawayRadioButtonClicked()

        verify(view).showTakeawayDiscountResult()
    }

    @Test
    fun `on takeaway radio button clicked and cafe takeaway discount is 0 EXPECT do not show order amount with takeaway`() {
        val orderSketch = orderSketch.copy(cafe = cafe.copy(takeawayDiscount = 0))
        val presenter = createPresenter(orderSketch)
        presenter.attachView(view)

        presenter.onTakeawayRadioButtonClicked()

        verify(view, never()).showTakeawayDiscountResult()
    }

    @Test
    fun `on takeaway radio button clicked EXPECT hide delivery cost result`() {
        presenter.attachView(view)

        verify(view).hideDeliveryCostResult()
    }

    @Test
    fun `on takeaway radio button clicked EXPECT enable result and button`() {
        presenter.attachView(view)

        verify(view).enableResultAndButton()
    }

    @Test
    fun `on delivery radio button clicked EXPECT select delivery receiving method`() {
        presenter.attachView(view)

        presenter.onDeliveryRadioButtonClicked()

        verify(view).selectDeliveryReceivingMethod()
    }

    @Test
    fun `on delivery radio button clicked and delivery allowed EXPECT show order amount with delivery`() {
        val orderSketch = orderSketch.copy(deliveryAllowed = true)
        val presenter = createPresenter(orderSketch)
        presenter.attachView(view)

        presenter.onDeliveryRadioButtonClicked()

        verify(view).showOrderAmountWithDelivery(orderSketch.orderWithDeliveryAmount)
    }

    @Test
    fun `on delivery radio button clicked and delivery allowed EXPECT show delivery cost result`() {
        val orderSketch = orderSketch.copy(deliveryAllowed = true)
        val presenter = createPresenter(orderSketch)
        presenter.attachView(view)

        presenter.onDeliveryRadioButtonClicked()

        verify(view).showDeliveryCostResult()
    }

    @Test
    fun `on delivery radio button clicked and delivery not allowed EXPECT hide delivery cost result`() {
        val orderSketch = orderSketch.copy(deliveryAllowed = false)
        val presenter = createPresenter(orderSketch)
        presenter.attachView(view)
        clearInvocations(view)

        presenter.onDeliveryRadioButtonClicked()

        verify(view).hideDeliveryCostResult()
    }

    @Test
    fun `on delivery radio button clicked and delivery not allowed EXPECT disable result and button`() {
        val orderSketch = orderSketch.copy(deliveryAllowed = false)
        val presenter = createPresenter(orderSketch)
        presenter.attachView(view)
        clearInvocations(view)

        presenter.onDeliveryRadioButtonClicked()

        verify(view).disableResultAndButton()
    }

    @Test
    fun `on delivery radio button clicked EXPECT hide takeaway discount result`() {
        presenter.attachView(view)

        presenter.onDeliveryRadioButtonClicked()

        verify(view).hideTakeawayDiscountResult()
    }

    @Test
    fun `takeaway selected and on create order button clicked and receiving method is takeaway EXPECT clear focus`() {
        whenever(createOrderUseCase(any())).thenReturn(Single.never())
        presenter.attachView(view)
        presenter.onTakeawayRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).clearFocus()
    }

    @Test
    fun `takeaway selected and on create order button clicked EXPECT validate name`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onTakeawayRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setNameValidationResult(validationResult)
    }

    @Test
    fun `on create order button clicked and receiving method is takeaway EXPECT validate phone`() {
        val validationResult = "result"
        whenever(validatePhoneUseCase(any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onTakeawayRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setPhoneValidationResult(validationResult)
    }

    @Test
    fun `on create order button clicked and receiving method is takeaway EXPECT validate email`() {
        val validationResult = "result"
        whenever(validateEmailUseCase(any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onTakeawayRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setEmailValidationResult(validationResult)
    }

    @Test
    fun `on create order button clicked and receiving method is takeaway EXPECT validate takeaway time`() {
        val validationResult = "result"
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onTakeawayRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setTakeawayTimeValidationResult(validationResult)
    }

    @Test
    fun `takeaway selected and on create order button clicked and name is invalid EXPECT request focus on first error and do not send order`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(validationResult)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        presenter.attachView(view)
        presenter.onTakeawayRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).requestFocusOnFirstError()
        verify(createOrderUseCase, never()).invoke(any())
    }

    @Test
    fun `takeaway selected and on create order button clicked and phone is invalid EXPECT request focus on first error and do not send order`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(validationResult)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        presenter.attachView(view)
        presenter.onTakeawayRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).requestFocusOnFirstError()
        verify(createOrderUseCase, never()).invoke(any())
    }

    @Test
    fun `takeaway selected and on create order button clicked and email is invalid EXPECT request focus on first error and do not send order`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(validationResult)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        presenter.attachView(view)
        presenter.onTakeawayRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).requestFocusOnFirstError()
        verify(createOrderUseCase, never()).invoke(any())
    }

    @Test
    fun `takeaway selected and on create order button clicked and takeaway time is invalid EXPECT request focus on first error and do not send order`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onTakeawayRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).requestFocusOnFirstError()
        verify(createOrderUseCase, never()).invoke(any())
    }

    @Test
    fun `takeaway selected and on create order button clicked and all fields is valid EXPECT show progress`() {
        whenever(createOrderUseCase(any())).thenReturn(Single.never())
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        presenter.attachView(view)
        presenter.onTakeawayRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).showProgress()
    }

    @Test
    fun `on create order button clicked and all fields is valid EXPECT create order use case`() {
        val order = Order.EMPTY.copy(cafe = orderSketch.cafe, productMap = orderSketch.products)
        whenever(createOrderUseCase(any())).thenReturn(Single.never())
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        presenter.onTakeawayRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(createOrderUseCase).invoke(order)
    }

    @Test
    fun `order created EXPECT hide progress`() {
        val orderId = "123"
        whenever(createOrderUseCase(any())).thenReturn(Single.just(orderId))
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        presenter.attachView(view)

        presenter.onCreateOrderButtonClicked()

        verify(view).hideProgress()
    }

    @Test
    fun `order created EXPECT clear basket`() {
        val orderId = "123"
        whenever(createOrderUseCase(any())).thenReturn(Single.just(orderId))
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)

        presenter.onCreateOrderButtonClicked()

        verify(clearBasketUseCase).invoke()
    }

    @Test
    fun `order created EXPECT route to confirm screen`() {
        val orderId = "123"
        whenever(createOrderUseCase(any())).thenReturn(Single.just(orderId))
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)

        presenter.onCreateOrderButtonClicked()

        verify(router).toConfirmation(orderId)
    }

    @Test
    fun `order creating failed EXPECT hide progress`() {
        val error = Throwable("error")
        whenever(createOrderUseCase(any())).thenReturn(Single.error(error))
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        whenever(errorConverter.convert(error)).thenReturn(ErrorType.BAD_INTERNET)
        presenter.attachView(view)

        presenter.onCreateOrderButtonClicked()

        verify(view).hideProgress()
    }

    @Test
    fun `order creating failed with bad internet EXPECT show no internet dialog`() {
        val error = Throwable("error")
        whenever(createOrderUseCase(any())).thenReturn(Single.error(error))
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        whenever(errorConverter.convert(error)).thenReturn(ErrorType.BAD_INTERNET)
        presenter.attachView(view)

        presenter.onCreateOrderButtonClicked()

        verify(view).showNoInternetDialog()
    }

    @Test
    fun `order creating failed with service unavailable EXPECT show service unavailable`() {
        val error = Throwable("error")
        whenever(createOrderUseCase(any())).thenReturn(Single.error(error))
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        whenever(errorConverter.convert(error)).thenReturn(ErrorType.SERVICE_UNAVAILABLE)
        presenter.attachView(view)

        presenter.onCreateOrderButtonClicked()

        verify(view).showServiceUnavailable()
    }

    @Test
    fun `on create order button clicked and receiving method is delivery EXPECT clear focus`() {
        whenever(createOrderUseCase(any())).thenReturn(Single.never())
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).clearFocus()
    }

    @Test
    fun `delivery selected and on create order button clicked EXPECT validate name`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setNameValidationResult(validationResult)
    }

    @Test
    fun `delivery selected and on create order button clicked EXPECT validate phone`() {
        val validationResult = "result"
        whenever(validatePhoneUseCase(any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setPhoneValidationResult(validationResult)
    }

    @Test
    fun `delivery selected and on create order button clicked EXPECT validate email`() {
        val validationResult = "result"
        whenever(validateEmailUseCase(any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setEmailValidationResult(validationResult)
    }

    @Test
    fun `delivery selected and on create order button clicked EXPECT validate delivery time`() {
        val validationResult = "result"
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setDeliveryTimeValidationResult(validationResult)
    }

    @Test
    fun `delivery selected and on create order button clicked EXPECT validate street`() {
        val validationResult = "result"
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setStreetValidationResult(validationResult)
    }

    @Test
    fun `delivery selected and on create order button clicked EXPECT validate house number`() {
        val validationResult = "result"
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setHouseNumberValidationResult(validationResult)
    }

    @Test
    fun `delivery selected and on create order button clicked EXPECT validate porch`() {
        val validationResult = "result"
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setPorchValidationResult(validationResult)
    }

    @Test
    fun `delivery selected and on create order button clicked EXPECT validate floor`() {
        val validationResult = "result"
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setFloorValidationResult(validationResult)
    }

    @Test
    fun `delivery selected and on create order button clicked EXPECT validate flat`() {
        val validationResult = "result"
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setFlatValidationResult(validationResult)
    }

    @Test
    fun `delivery selected and on create order button clicked EXPECT validate comment`() {
        val validationResult = "result"
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).setCommentValidationResult(validationResult)
    }

    @Test
    fun `delivery selected and on create order button clicked and name is invalid EXPECT request focus on first error and do not send order`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(validationResult)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(null)
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(null)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).requestFocusOnFirstError()
        verify(createOrderUseCase, never()).invoke(any())
    }

    @Test
    fun `delivery selected and on create order button clicked and phone is invalid EXPECT request focus on first error and do not send order`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(validationResult)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(null)
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(null)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).requestFocusOnFirstError()
        verify(createOrderUseCase, never()).invoke(any())
    }

    @Test
    fun `delivery selected and on create order button clicked and email is invalid EXPECT request focus on first error and do not send order`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(validationResult)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(null)
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(null)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).requestFocusOnFirstError()
        verify(createOrderUseCase, never()).invoke(any())
    }

    @Test
    fun `delivery selected and on create order button clicked and export time is invalid EXPECT request focus on first error and do not send order`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(validationResult)
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(null)
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(null)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).requestFocusOnFirstError()
        verify(createOrderUseCase, never()).invoke(any())
    }

    @Test
    fun `delivery selected and on create order button clicked and some of common string is invalid EXPECT request focus on first error and do not send order`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(validationResult)
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(null)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).requestFocusOnFirstError()
        verify(createOrderUseCase, never()).invoke(any())
    }

    @Test
    fun `delivery selected and on create order button clicked and some of single number is invalid EXPECT request focus on first error and do not send order`() {
        val validationResult = "result"
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(null)
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(validationResult)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).requestFocusOnFirstError()
        verify(createOrderUseCase, never()).invoke(any())
    }

    @Test
    fun `delivery selected and on create order button clicked and all fields is valid EXPECT show progress`() {
        whenever(createOrderUseCase(any())).thenReturn(Single.never())
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(null)
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(null)
        presenter.attachView(view)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(view).showProgress()
    }

    @Test
    fun `delivery selected and on create order button clicked and all fields is valid EXPECT create order`() {
        val order = Order.EMPTY.copy(cafe = orderSketch.cafe, productMap = orderSketch.products, receiveMethod = ReceiveMethod.DELIVERY)
        whenever(createOrderUseCase(any())).thenReturn(Single.never())
        whenever(validateNameUseCase(any())).thenReturn(null)
        whenever(validatePhoneUseCase(any())).thenReturn(null)
        whenever(validateEmailUseCase(any())).thenReturn(null)
        whenever(validateExportTimeUseCase(any(), any())).thenReturn(null)
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(null)
        whenever(validateSingleNumberUseCase(any(), any())).thenReturn(null)
        presenter.onDeliveryRadioButtonClicked()

        presenter.onCreateOrderButtonClicked()

        verify(createOrderUseCase).invoke(order)
    }

    @Test
    fun `negative button clicked EXPECT back to start point`() {
        presenter.onNegativeButtonClicked()

        verify(router).backToStartPoint()
    }

    @Test
    fun `on retry clicked EXPECT create order`() {
        whenever(createOrderUseCase(any())).thenReturn(Single.never())
        presenter.onRetryClicked()

        verify(createOrderUseCase).invoke(any())
    }

    @Test
    fun `on privacy policy clicked EXPECT route to privacy policy`() {
        presenter.onPrivacyPolicyClicked()

        verify(router).toPrivacyPolicy()
    }

    @Test
    fun `on name focus lost EXPECT validate name`() {
        val validationResult = "result"
        val value = "value"
        whenever(validateNameUseCase(value)).thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(value, OrderValidatorField.NAME)

        verify(view).setNameValidationResult(validationResult)
    }

    @Test
    fun `on phone focus lost EXPECT validate phone`() {
        val validationResult = "result"
        val value = "value"
        whenever(validatePhoneUseCase(value)).thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(value, OrderValidatorField.PHONE)

        verify(view).setPhoneValidationResult(validationResult)
    }

    @Test
    fun `on email focus lost EXPECT validate email`() {
        val validationResult = "result"
        val value = "value"
        whenever(validateEmailUseCase(value)).thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(value, OrderValidatorField.EMAIL)

        verify(view).setEmailValidationResult(validationResult)
    }

    @Test
    fun `on street focus lost EXPECT validate street`() {
        val validationResult = "result"
        val value = "value"
        whenever(validateCommonStringUseCase(value, required = true)).thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(value, OrderValidatorField.STREET)

        verify(view).setStreetValidationResult(validationResult)
    }

    @Test
    fun `on house number focus lost EXPECT validate house number`() {
        val validationResult = "result"
        val value = "value"
        whenever(validateSingleNumberUseCase(value, required = true)).thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(value, OrderValidatorField.HOUSE_NUMBER)

        verify(view).setHouseNumberValidationResult(validationResult)
    }

    @Test
    fun `on porch focus lost EXPECT validate porch`() {
        val validationResult = "result"
        val value = "value"
        whenever(validateSingleNumberUseCase(value, required = false)).thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(value, OrderValidatorField.PORCH)

        verify(view).setPorchValidationResult(validationResult)
    }

    @Test
    fun `on floor focus lost EXPECT validate floor`() {
        val validationResult = "result"
        val value = "value"
        whenever(validateSingleNumberUseCase(value, required = false)).thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(value, OrderValidatorField.FLOOR)

        verify(view).setFloorValidationResult(validationResult)
    }

    @Test
    fun `on flat focus lost EXPECT validate flat`() {
        val validationResult = "result"
        val value = "value"
        whenever(validateSingleNumberUseCase(value, required = true)).thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(value, OrderValidatorField.FLAT)

        verify(view).setFlatValidationResult(validationResult)
    }

    @Test
    fun `on comment focus lost EXPECT validate comment`() {
        val validationResult = "result"
        val value = "value"
        whenever(validateCommonStringUseCase(value, required = false)).thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(value, OrderValidatorField.COMMENT)

        verify(view).setCommentValidationResult(validationResult)
    }

    private fun createPresenter(orderSketch: OrderSketch) = OrderRegistrationPresenter(
        validateNameUseCase,
        validatePhoneUseCase,
        validateEmailUseCase,
        validateCommonStringUseCase,
        validateSingleNumberUseCase,
        validateExportTimeUseCase,
        clearBasketUseCase,
        createOrderUseCase,
        getPhoneCountryPrefixUseCase,
        getIntervalListUseCase,
        errorConverter,
        router,
        orderSketch
    )
}