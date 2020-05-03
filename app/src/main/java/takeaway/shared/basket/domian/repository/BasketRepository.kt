package takeaway.shared.basket.domian.repository

import takeaway.feature.cafe.domain.entity.Product
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.shared.basket.domian.entity.Basket

interface BasketRepository {

    fun addToBasket(product: Product, count: Int, cafe: Cafe)

    fun clearBasket()

    fun getBasket(): Basket

    fun getBasketAmount(): Int

    fun getBasketCafeId(): String?
}