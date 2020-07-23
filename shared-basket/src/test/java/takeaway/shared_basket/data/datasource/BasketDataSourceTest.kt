package takeaway.shared_basket.data.datasource

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_basket.basket
import takeaway.shared_basket.cafe
import takeaway.shared_basket.domain.entity.Basket
import takeaway.shared_basket.product
import takeaway.shared_basket.productMap

@RunWith(MockitoJUnitRunner::class)
class BasketDataSourceTest {

    private val dataSource = BasketDataSource()

    @Test
    fun `add to basket and cafe is new EXPECT save this cafe`() {
        dataSource.addToBasket(product, 1, cafe)

        assertEquals(cafe.id, dataSource.getBasketCafeId())
    }

    @Test
    fun `add to basket EXPECT product in basket`() {
        dataSource.addToBasket(product, 1, cafe)

        assertEquals(basket, dataSource.getBasket())
    }

    @Test
    fun `add to basket EXPECT basket amount increases by the cost of the product`() {
        val countOfProduct = 2

        dataSource.addToBasket(product, countOfProduct, cafe)

        assertEquals(product.price * countOfProduct, dataSource.getBasketAmount())
    }

    @Test
    fun `clear basket EXPECT basket is empty`() {
        dataSource.addToBasket(product, 1, cafe)

        dataSource.clearBasket()

        assertEquals(Basket(null, null), dataSource.getBasket())
    }

    @Test
    fun `get basket EXPECT basket is empty`() {
        assertEquals(Basket(null, null), dataSource.getBasket())
    }

    @Test
    fun `get basket amount EXPECT 0`() {
        assertEquals(0, dataSource.getBasketAmount())
    }

    @Test
    fun `get cafe id EXPECT null`() {
        assertEquals(null, dataSource.getBasketCafeId())
    }

    @Test
    fun `delete product from basket and basket have only this product EXPECT basket products is empty`() {
        val countOfProduct = 2
        dataSource.addToBasket(product, countOfProduct, cafe)

        dataSource.deleteProductFromBasket(product.id)

        assertEquals(Basket(cafe, emptyMap()), dataSource.getBasket())
    }

    @Test
    fun `delete product from basket and basket have only this product EXPECT basket amount is 0`() {
        val countOfProduct = 2
        dataSource.addToBasket(product, countOfProduct, cafe)

        dataSource.deleteProductFromBasket(product.id)

        assertEquals(0, dataSource.getBasketAmount())
    }

    @Test
    fun `delete product from basket and basket have several product EXPECT basket without this product`() {
        val secondProductId = "23"
        dataSource.addToBasket(product, 1, cafe)
        dataSource.addToBasket(product.copy(id = secondProductId), 1, cafe)

        dataSource.deleteProductFromBasket(secondProductId)

        assertEquals(Basket(cafe, productMap), dataSource.getBasket())
    }

    @Test
    fun `delete product from basket and basket have several product EXPECT decreased by product cost`() {
        val secondProductId = "23"
        dataSource.addToBasket(product, 1, cafe)
        dataSource.addToBasket(product.copy(id = secondProductId), 1, cafe)

        dataSource.deleteProductFromBasket(secondProductId)

        assertEquals(product.price, dataSource.getBasketAmount())
    }
}

