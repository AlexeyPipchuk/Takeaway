package takeaway.shared.basket.domian.repository

import takeaway.shared.basket.domian.entity.Basket
import takeaway.shared_cafe.domain.entity.Cafe

interface BasketRepository {

    fun addToBasket(product: domain.entity.Product, count: Int, cafe: Cafe)

    fun clearBasket()

    fun getBasket(): Basket

    fun getBasketAmount(): Int

    fun getBasketCafeId(): String?

    fun deleteProductFromBasket(productId: String)
}