package takeaway.shared_basket.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_basket.domain.repository.BasketRepository

@RunWith(MockitoJUnitRunner::class)
class DeleteProductFromBasketUseCaseTest {

    private val basketRepository: BasketRepository = mock()
    private val useCase = DeleteProductFromBasketUseCase(basketRepository)

    @Test
    fun `invoke EXPECT clear basket`() {
        val productId = "1"

        useCase(productId)

        verify(basketRepository).deleteProductFromBasket(productId)
    }
}