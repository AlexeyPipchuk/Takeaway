package takeaway.feature_basket.presentation

import base.BasePresenter
import domain.entity.OrderSketch
import domain.entity.Product
import takeaway.feature_basket.presentation.model.BasketItem
import takeaway.shared_basket.domain.entity.Basket
import takeaway.shared_basket.domain.usecase.ClearBasketUseCase
import takeaway.shared_basket.domain.usecase.DeleteProductFromBasketUseCase
import takeaway.shared_basket.domain.usecase.GetBasketAmountUseCase
import takeaway.shared_basket.domain.usecase.GetBasketUseCase
import javax.inject.Inject

class BasketPresenter @Inject constructor(
    private val getBasketUseCase: GetBasketUseCase,
    private val deleteProductFromBasketUseCase: DeleteProductFromBasketUseCase,
    private val clearBasketUseCase: ClearBasketUseCase,
    private val getBasketAmountUseCase: GetBasketAmountUseCase,
    private val router: BasketRouter
) : BasePresenter<BasketView>() {

    private var deliveryDiscountCalculated = 0

    override fun onViewAttach() {
        super.onViewAttach()

        updateBasket()
    }

    private fun updateBasket(basket: Basket = getBasketUseCase()) {
        if (basket.isBasketNotEmpty()) {
            basket.cafe?.let { view?.showOrderCafeInfo(it) }
            basket.products?.let {
                view?.showBasketItems(toBasketItemList(it))
            }
            val basketAmount = getBasketAmountUseCase()

            view?.setBasketAmount(basketAmount)
            basket.cafe?.takeawayDiscount?.let { showCalculatedTakeawayDiscount(it, basketAmount) }
            calculateHelpValues(basketAmount, basket)
        } else {
            view?.showEmptyContent()
        }
    }

    private fun Basket.isBasketNotEmpty() =
        cafe != null && !products.isNullOrEmpty()

    private fun showCalculatedTakeawayDiscount(takeawayDiscount: Int, basketAmount: Int) {
        val takeawayDiscountCalculated = calculateTakeawayDiscount(takeawayDiscount, basketAmount)
        if (takeawayDiscountCalculated != 0) {
            view?.showTakeawayDiscountCalculated(takeawayDiscount, takeawayDiscountCalculated)
        }
    }

    private fun calculateTakeawayDiscount(takeawayDiscount: Int, basketAmount: Int): Int =
        takeawayDiscount * basketAmount / 100

    private fun calculateHelpValues(
        basketAmount: Int,
        basket: Basket
    ) {
        view?.setDefaultHelpMessagesState()

        val minDeliverySum = basket.cafe?.minDeliverySum
        val deliveryPrice = basket.cafe?.deliveryPrice
        val deliveryFreeFrom = basket.cafe?.deliveryFreeFrom

        minDeliverySum?.let {
            if (basketAmount < minDeliverySum) {
                deliveryDiscountCalculated = 0
                view?.showMinDeliverySum(minDeliverySum)
            } else {
                val deliveryPriceCalculated =
                    if (deliveryFreeFrom != null) {
                        if (basketAmount < deliveryFreeFrom) deliveryPrice else 0
                    } else deliveryPrice

                deliveryDiscountCalculated = deliveryPriceCalculated ?: 0
                view?.showDeliveryPriceCalculated(deliveryPriceCalculated!!)

                if (deliveryPriceCalculated != 0) {
                    deliveryFreeFrom?.let {
                        val valueToFreeDelivery = deliveryFreeFrom - basketAmount
                        view?.showMessageForFreeDelivery(valueToFreeDelivery)
                    }
                } else Unit
            }
        }
    }

    fun onBackButtonClick() {
        router.backToCafe()
    }

    fun onToOrderRegistrationButtonClicked() {
        val resultProductAmount = getBasketAmountUseCase()
        val resultBasket = getBasketUseCase()
        val resultCafe = resultBasket.cafe!!

        var valueToFreeDelivery = resultCafe.deliveryFreeFrom - resultProductAmount
        if (valueToFreeDelivery < 0) valueToFreeDelivery = 0

        val takeawayDiscountCalculated = calculateTakeawayDiscount(
            resultCafe.takeawayDiscount,
            resultProductAmount
        )

        router.toOrderRegistration(
            OrderSketch(
                cafe = resultCafe,
                basketAmountWithoutAll = resultProductAmount,
                products = resultBasket.products!!,
                takeawayDiscountCalculated = takeawayDiscountCalculated,
                orderWithTakeawayAmount = resultProductAmount - takeawayDiscountCalculated,
                deliveryCostCalculated = deliveryDiscountCalculated,
                orderWithDeliveryAmount = resultProductAmount + deliveryDiscountCalculated,
                deliveryAllowed = resultProductAmount >= resultCafe.minDeliverySum,
                valueToFreeDelivery = valueToFreeDelivery
            )
        )
    }

    fun onProductDeleteClicked(product: BasketItem) {
        deleteProductFromBasketUseCase(product.productId)

        val basket = getBasketUseCase()
        if (!basket.isBasketNotEmpty()) {
            clearBasketUseCase()
        }
        updateBasket(basket)
    }

    private fun toBasketItemList(productMap: Map<Product, Int>): List<BasketItem> =
        productMap.entries.map {
            BasketItem(
                it.key.id,
                it.key.title,
                it.key.price * it.value,
                it.value
            )
        }
}