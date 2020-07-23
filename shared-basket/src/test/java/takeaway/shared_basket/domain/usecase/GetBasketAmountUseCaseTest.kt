package takeaway.shared_basket.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_basket.domain.repository.BasketRepository

@RunWith(MockitoJUnitRunner::class)
class GetBasketAmountUseCaseTest {

    private val basketRepository: BasketRepository = mock()
    private val useCase = GetBasketAmountUseCase(basketRepository)

    @Test
    fun `invoke EXPECT basket amount`() {
        val basketAmount = 1000
        whenever(basketRepository.getBasketAmount()).thenReturn(basketAmount)

        assertEquals(basketAmount, useCase())
    }
}