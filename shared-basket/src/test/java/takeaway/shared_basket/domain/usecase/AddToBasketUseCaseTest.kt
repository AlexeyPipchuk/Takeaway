package takeaway.shared_basket.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_basket.cafe
import takeaway.shared_basket.domain.repository.BasketRepository
import takeaway.shared_basket.product

@RunWith(MockitoJUnitRunner::class)
class AddToBasketUseCaseTest {

    private val basketRepository: BasketRepository = mock()
    private val useCase = AddToBasketUseCase(basketRepository)

    @Test
    fun `invoke EXPECT add product and cafe to basket`() {
        useCase(product, 1, cafe)

        verify(basketRepository).addToBasket(product, 1, cafe)
    }
}