package takeaway.shared_basket.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_basket.basket
import takeaway.shared_basket.domain.repository.BasketRepository

@RunWith(MockitoJUnitRunner::class)
class GetBasketUseCaseTest {

    private val basketRepository: BasketRepository = mock()
    private val useCase = GetBasketUseCase(basketRepository)

    @Test
    fun `invoke EXPECT basket`() {
        whenever(basketRepository.getBasket()).thenReturn(basket)

        assertEquals(basket, useCase())
    }
}