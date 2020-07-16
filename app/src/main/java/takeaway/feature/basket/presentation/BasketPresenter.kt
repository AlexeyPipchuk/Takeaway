package takeaway.feature.basket.presentation

import base.BasePresenter
import ru.terrakok.cicerone.Router
import takeaway.app.navigation.Screen
import takeaway.feature.basket.model.BasketItem
import takeaway.feature.basket.ui.BasketView
import takeaway.shared_basket.domain.entity.Basket
import takeaway.shared_basket.domain.usecase.ClearBasketUseCase
import takeaway.shared_basket.domain.usecase.DeleteProductFromBasketUseCase
import takeaway.shared_basket.domain.usecase.GetBasketAmountUseCase
import takeaway.shared_basket.domain.usecase.GetBasketUseCase
import javax.inject.Inject

class BasketPresenter @Inject constructor(
    private val getBasketUseCase: takeaway.shared_basket.domain.usecase.GetBasketUseCase,
    private val deleteProductFromBasketUseCase: takeaway.shared_basket.domain.usecase.DeleteProductFromBasketUseCase,
    private val clearBasketUseCase: takeaway.shared_basket.domain.usecase.ClearBasketUseCase,
    private val getBasketAmountUseCase: takeaway.shared_basket.domain.usecase.GetBasketAmountUseCase,
    private val router: Router
) : BasePresenter<BasketView>() {

    private var deliveryDiscountCalculated = 0

    override fun onViewAttach() {
        super.onViewAttach()

        updateBasket()
    }

    private fun updateBasket(basket: takeaway.shared_basket.domain.entity.Basket = getBasketUseCase()) {
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

    private fun takeaway.shared_basket.domain.entity.Basket.isBasketNotEmpty() =
        cafe != null && !products.isNullOrEmpty()

    private fun showCalculatedTakeawayDiscount(takeawayDiscount: Int, basketAmount: Int) {
        val takeawayDiscountCalculated = calculateTakeawayDiscount(takeawayDiscount, basketAmount)
        if (takeawayDiscountCalculated != 0) {
            view?.showTakeawayDiscountCalculated(takeawayDiscount, takeawayDiscountCalculated)
        }
    }

    private fun calculateTakeawayDiscount(takeawayDiscount: Int, basketAmount: Int): Int =
        takeawayDiscount * basketAmount / 100

    private fun calculateHelpValues(basketAmount: Int, basket: takeaway.shared_basket.domain.entity.Basket) {
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
        router.exit()
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

        router.navigateTo(
            Screen.OrderRegistrationScreen(
                domain.entity.OrderSketch(
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

    private fun toBasketItemList(productMap: Map<domain.entity.Product, Int>): List<BasketItem> =
        productMap.entries.map {
            BasketItem(
                it.key.id,
                it.key.title,
                it.key.price * it.value,
                it.value
            )
        }
}