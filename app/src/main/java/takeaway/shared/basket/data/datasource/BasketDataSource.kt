package takeaway.shared.basket.data.datasource

import takeaway.feature.cafe.domain.entity.Product
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.shared.basket.domian.entity.Basket
import javax.inject.Inject

interface BasketDataSource {

    fun addToBasket(product: Product, count: Int, cafe: Cafe)

    fun clearBasket()

    fun getBasket(): Basket?

    fun getBasketAmount(): Int

    fun getBasketCafeId(): String?
}

class BasketDataSourceImpl @Inject constructor() : BasketDataSource {

    private var productInBasket: MutableMap<Product, Int>? = null
    private var cafe: Cafe? = null
    private var amount: Int = 0

    override fun addToBasket(product: Product, count: Int, cafe: Cafe) {
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

    override fun clearBasket() {
        productInBasket = null
        cafe = null
        amount = 0
    }

    override fun getBasket(): Basket = Basket(cafe, productInBasket)

    override fun getBasketAmount(): Int = amount

    override fun getBasketCafeId(): String? = cafe?.id
}