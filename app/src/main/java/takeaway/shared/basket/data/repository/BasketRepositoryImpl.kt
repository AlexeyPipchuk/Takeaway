package takeaway.shared.basket.data.repository

import takeaway.shared.basket.data.datasource.BasketDataSource
import takeaway.shared.basket.domian.entity.Basket
import takeaway.shared.basket.domian.repository.BasketRepository
import takeaway.shared_cafe.domain.entity.Cafe
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val dataSource: BasketDataSource
) : BasketRepository {

    override fun addToBasket(product: domain.entity.Product, count: Int, cafe: Cafe) {
        dataSource.addToBasket(product, count, cafe)
    }

    override fun clearBasket() {
        dataSource.clearBasket()
    }

    override fun getBasket(): Basket =
        dataSource.getBasket()

    override fun getBasketAmount(): Int =
        dataSource.getBasketAmount()

    override fun getBasketCafeId(): String? =
        dataSource.getBasketCafeId()

    override fun deleteProductFromBasket(productId: String) {
        dataSource.deleteProductFromBasket(productId)
    }
}