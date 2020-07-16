package takeaway.shared_basket.domain.repository

import domain.entity.Product
import takeaway.shared_basket.domain.entity.Basket
import takeaway.shared_cafe.domain.entity.Cafe

interface BasketRepository {

    fun addToBasket(product: Product, count: Int, cafe: Cafe)

    fun clearBasket()

    fun getBasket(): Basket

    fun getBasketAmount(): Int

    fun getBasketCafeId(): String?

    fun deleteProductFromBasket(productId: String)
}