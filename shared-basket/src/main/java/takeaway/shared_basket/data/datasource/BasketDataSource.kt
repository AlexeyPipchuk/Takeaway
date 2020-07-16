package takeaway.shared_basket.data.datasource

import domain.entity.Product
import takeaway.shared_basket.domain.entity.Basket
import takeaway.shared_cafe.domain.entity.Cafe
import javax.inject.Inject

class BasketDataSource @Inject constructor() {

    private var productInBasket: MutableMap<Product, Int>? = null
    private var cafe: Cafe? = null
    private var amount: Int = 0

    fun addToBasket(product: Product, count: Int, cafe: Cafe) {
        if (this.cafe != cafe) this.cafe = cafe

        if (productInBasket == null) {
            productInBasket = mutableMapOf()
        }

        addToBasket(product, count)
    }

    private fun addToBasket(product: Product, count: Int) {
        productInBasket?.let {
            val currentProductCount = it[product] ?: 0
            productInBasket!![product] = currentProductCount + count
            amount += product.price * count
        }
    }

    fun clearBasket() {
        productInBasket = null
        cafe = null
        amount = 0
    }

    fun getBasket(): Basket = Basket(cafe, productInBasket)

    fun getBasketAmount(): Int = amount

    fun getBasketCafeId(): String? = cafe?.id

    fun deleteProductFromBasket(productId: String) {
        if (productInBasket != null) {
            val removedProduct = productInBasket?.entries?.find { it.key.id == productId }
            removedProduct?.let {
                amount -= removedProduct.key.price * removedProduct.value
                productInBasket?.entries?.remove(removedProduct)
            }
        }
    }
}