package takeaway.feature_basket.presentation

import com.nhaarman.mockitokotlin2.*
import domain.entity.OrderSketch
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.feature_basket.*
import takeaway.shared_basket.domain.entity.Basket
import takeaway.shared_basket.domain.usecase.ClearBasketUseCase
import takeaway.shared_basket.domain.usecase.DeleteProductFromBasketUseCase
import takeaway.shared_basket.domain.usecase.GetBasketAmountUseCase
import takeaway.shared_basket.domain.usecase.GetBasketUseCase

@RunWith(MockitoJUnitRunner::class)
class BasketPresenterTest {

    private val getBasketUseCase: GetBasketUseCase = mock()
    private val deleteProductFromBasketUseCase: DeleteProductFromBasketUseCase = mock()
    private val clearBasketUseCase: ClearBasketUseCase = mock()
    private val getBasketAmountUseCase: GetBasketAmountUseCase = mock()
    private val router: BasketRouter = mock()

    private val view: BasketView = mock()

    private val presenter = BasketPresenter(
        getBasketUseCase,
        deleteProductFromBasketUseCase,
        clearBasketUseCase,
        getBasketAmountUseCase,
        router
    )

    @Test
    fun `view attached and basket is empty EXPECT show empty content`() {
        whenever(getBasketUseCase()).thenReturn(emptyBasket)

        presenter.attachView(view)

        verify(getBasketUseCase).invoke()
        verify(view).showEmptyContent()
    }

    @Test
    fun `view attached cafe is not empty EXPECT show cafe info`() {
        whenever(getBasketUseCase()).thenReturn(basket)

        presenter.attachView(view)

        verify(view).showOrderCafeInfo(cafe)
    }

    @Test
    fun `view attached product list is not empty EXPECT show products`() {
        whenever(getBasketUseCase()).thenReturn(basket)

        presenter.attachView(view)

        verify(view).showBasketItems(listOf(productItem))
    }

    @Test
    fun `view attached and basket is not empty EXPECT set basket amount`() {
        val basketAmount = 100
        whenever(getBasketAmountUseCase()).thenReturn(basketAmount)
        whenever(getBasketUseCase()).thenReturn(basket)

        presenter.attachView(view)

        verify(getBasketAmountUseCase).invoke()
        verify(view).setBasketAmount(basketAmount)
    }

    @Test
    fun `view attached and takeaway discount is not empty EXPECT show calculated takeaway discount`() {
        val basketAmount = 100
        val takeawayDiscountCalculated = takeawayDiscount * basketAmount / 100
        whenever(getBasketAmountUseCase()).thenReturn(basketAmount)
        whenever(getBasketUseCase()).thenReturn(basket)

        presenter.attachView(view)

        verify(view).showTakeawayDiscountCalculated(takeawayDiscount, takeawayDiscountCalculated)
    }

    @Test
    fun `view attached and takeaway discount is not empty but calculated discount is 0 EXPECT not show calculated takeaway discount`() {
        val basketAmount = 0
        whenever(getBasketAmountUseCase()).thenReturn(basketAmount)
        whenever(getBasketUseCase()).thenReturn(basket)

        presenter.attachView(view)

        verify(view, never()).showTakeawayDiscountCalculated(any(), any())
    }

    @Test
    fun `view attached EXPECT set default help messages state`() {
        whenever(getBasketUseCase()).thenReturn(basket)

        presenter.attachView(view)

        verify(view).setDefaultHelpMessagesState()
    }

    @Test
    fun `view attached and min delivery sum is not null and less than basket amount EXPECT show min delivery sum`() {
        val basketAmount = 0
        whenever(getBasketAmountUseCase()).thenReturn(basketAmount)
        whenever(getBasketUseCase()).thenReturn(basket)

        presenter.attachView(view)

        verify(view).showMinDeliverySum(minDeliverySum)
    }

    @Test
    fun `view attached and basket amount more than min delivery sum, but less than delivery free from EXPECT show delivery price as delivery price`() {
        val basketAmount = 10
        val deliveryFreeFrom = 100
        whenever(getBasketAmountUseCase()).thenReturn(basketAmount)
        whenever(getBasketUseCase()).thenReturn(
            Basket(
                cafe.copy(
                    minDeliverySum = 0,
                    deliveryFreeFrom = deliveryFreeFrom
                ), productMap
            )
        )

        presenter.attachView(view)

        verify(view).showDeliveryPriceCalculated(deliveryPrice)
    }

    @Test
    fun `view attached and basket amount more than min delivery sum and more than delivery free from EXPECT show delivery price as 0`() {
        val basketAmount = 100
        val deliveryFreeFrom = 10
        whenever(getBasketAmountUseCase()).thenReturn(basketAmount)
        whenever(getBasketUseCase()).thenReturn(
            Basket(
                cafe.copy(deliveryFreeFrom = deliveryFreeFrom),
                productMap
            )
        )

        presenter.attachView(view)

        verify(view).showDeliveryPriceCalculated(0)
    }

    @Test
    fun `view attached and delivery cost not 0 EXPECT show message for free delivery`() {
        val deliveryFreeFrom = 100
        val basketAmount = 10
        val valueToFreeDelivery = deliveryFreeFrom - basketAmount
        whenever(getBasketAmountUseCase()).thenReturn(basketAmount)
        whenever(getBasketUseCase()).thenReturn(
            Basket(
                cafe.copy(
                    minDeliverySum = 0,
                    deliveryFreeFrom = deliveryFreeFrom
                ), productMap
            )
        )

        presenter.attachView(view)

        verify(view).showMessageForFreeDelivery(valueToFreeDelivery)
    }

    @Test
    fun `on back clicked EXPECT back to cafe`() {
        presenter.onBackButtonClick()

        verify(router).backToCafe()
    }

    @Test
    fun `on order registration button clicked and basket amount more than delivery free from EXPECT route to order registration with value to free delivery is 0`() {
        val basketAmount = 1000000
        val deliveryDiscountCalculated = 0
        val takeawayDiscountCalculated = takeawayDiscount * basketAmount / 100
        whenever(getBasketAmountUseCase()).thenReturn(basketAmount)
        whenever(getBasketUseCase()).thenReturn(basket)
        val orderSketch = OrderSketch(
            cafe = cafe,
            basketAmountWithoutAll = basketAmount,
            products = productMap,
            takeawayDiscountCalculated = takeawayDiscountCalculated,
            orderWithTakeawayAmount = basketAmount - takeawayDiscountCalculated,
            deliveryCostCalculated = deliveryDiscountCalculated,
            orderWithDeliveryAmount = basketAmount + deliveryDiscountCalculated,
            deliveryAllowed = basketAmount >= minDeliverySum,
            valueToFreeDelivery = 0
        )

        presenter.onToOrderRegistrationButtonClicked()

        verify(router).toOrderRegistration(orderSketch)
    }

    @Test
    fun `on order registration button clicked and basket amount less than delivery free from EXPECT route to order registration`() {
        val basketAmount = 1
        val deliveryDiscountCalculated = 0
        val takeawayDiscountCalculated = takeawayDiscount * basketAmount / 100
        whenever(getBasketAmountUseCase()).thenReturn(basketAmount)
        whenever(getBasketUseCase()).thenReturn(basket)
        val orderSketch = OrderSketch(
            cafe = cafe,
            basketAmountWithoutAll = basketAmount,
            products = productMap,
            takeawayDiscountCalculated = takeawayDiscountCalculated,
            orderWithTakeawayAmount = basketAmount - takeawayDiscountCalculated,
            deliveryCostCalculated = deliveryDiscountCalculated,
            orderWithDeliveryAmount = basketAmount + deliveryDiscountCalculated,
            deliveryAllowed = basketAmount >= minDeliverySum,
            valueToFreeDelivery = cafe.deliveryFreeFrom - basketAmount
        )

        presenter.onToOrderRegistrationButtonClicked()

        verify(router).toOrderRegistration(orderSketch)
    }

    @Test
    fun `on product delete clicked EXPECT delete product from basket use case called`() {
        whenever(getBasketUseCase()).thenReturn(basket)

        presenter.onProductDeleteClicked(productItem)

        verify(deleteProductFromBasketUseCase).invoke(productItem.productId)
    }

    @Test
    fun `on product delete clicked and basket is empty EXPECT clear basket use case called`() {
        whenever(getBasketUseCase()).thenReturn(emptyBasket)

        presenter.onProductDeleteClicked(productItem)

        verify(clearBasketUseCase).invoke()
    }

    @Test
    fun `on product delete clicked and basket is not empty EXPECT clear basket use case not called`() {
        whenever(getBasketUseCase()).thenReturn(basket)

        presenter.onProductDeleteClicked(productItem)

        verify(clearBasketUseCase, never()).invoke()
    }
}