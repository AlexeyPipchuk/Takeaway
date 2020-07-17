package takeaway.shared_basket.data.repository

import domain.entity.Product
import takeaway.shared_basket.data.datasource.BasketDataSource
import takeaway.shared_basket.domain.entity.Basket
import takeaway.shared_basket.domain.repository.BasketRepository
import takeaway.shared_cafe.domain.entity.Cafe
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val dataSource: BasketDataSource
) : BasketRepository {

    override fun addToBasket(product: Product, count: Int, cafe: Cafe) {
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