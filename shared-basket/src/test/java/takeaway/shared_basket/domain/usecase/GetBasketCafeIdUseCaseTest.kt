package takeaway.shared_basket.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_basket.domain.repository.BasketRepository

@RunWith(MockitoJUnitRunner::class)
class GetBasketCafeIdUseCaseTest {

    private val basketRepository: BasketRepository = mock()
    private val useCase = GetBasketCafeIdUseCase(basketRepository)

    @Test
    fun `invoke EXPECT basket cafe id`() {
        val basketCafeId = "12"
        whenever(basketRepository.getBasketCafeId()).thenReturn(basketCafeId)

        assertEquals(basketCafeId, useCase())
    }
}