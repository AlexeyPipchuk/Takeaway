package takeaway.feature_order_registration.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.feature_order_registration.domain.repository.CreateOrderRepository

@RunWith(MockitoJUnitRunner::class)
class CreateOrderUseCaseTest {

    private val orderRepository: CreateOrderRepository = mock()
    private val createOrderUseCase = CreateOrderUseCase(orderRepository)

    //TODO(Написать тесты на репу/датасорс, как будет закончена фича)
//    @Test
//    fun `invoke EXPECT create order`() {
//
//    }
}