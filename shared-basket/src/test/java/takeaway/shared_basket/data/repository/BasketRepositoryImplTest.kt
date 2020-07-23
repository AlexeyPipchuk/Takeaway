package takeaway.shared_basket.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_basket.basket
import takeaway.shared_basket.cafe
import takeaway.shared_basket.data.datasource.BasketDataSource
import takeaway.shared_basket.product

@RunWith(MockitoJUnitRunner::class)
class BasketRepositoryImplTest {

    private val basketDataSource: BasketDataSource = mock()
    private val repository = BasketRepositoryImpl(basketDataSource)

    @Test
    fun `add to basket EXPECT add product and cafe to basket`() {
        repository.addToBasket(product, 1, cafe)

        verify(basketDataSource).addToBasket(product, 1, cafe)
    }

    @Test
    fun `clear basket EXPECT clear basket`() {
        repository.clearBasket()

        verify(basketDataSource).clearBasket()
    }

    @Test
    fun `get basket EXPECT basket`() {
        whenever(basketDataSource.getBasket()).thenReturn(basket)

        assertEquals(basket, repository.getBasket())
    }

    @Test
    fun `get basket amount EXPECT basket amount`() {
        val basketAmount = 100
        whenever(basketDataSource.getBasketAmount()).thenReturn(basketAmount)

        assertEquals(basketAmount, repository.getBasketAmount())
    }

    @Test
    fun `get basket cafe id EXPECT basket cafe id`() {
        val basketCafeId = "1"
        whenever(basketDataSource.getBasketCafeId()).thenReturn(basketCafeId)

        assertEquals(basketCafeId, repository.getBasketCafeId())
    }

    @Test
    fun `delete product from basket EXPECT delete product from basket`() {
        val productId = "1"

        repository.deleteProductFromBasket(productId)

        verify(basketDataSource).deleteProductFromBasket(productId)
    }
}