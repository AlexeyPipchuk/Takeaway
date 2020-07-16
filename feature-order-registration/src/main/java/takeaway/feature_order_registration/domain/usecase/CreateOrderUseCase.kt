package takeaway.feature_order_registration.domain.usecase

import io.reactivex.Single
import takeaway.feature_order_registration.domain.entity.Order
import takeaway.feature_order_registration.domain.repository.CreateOrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val createOrderRepository: CreateOrderRepository
) {

    operator fun invoke(order: Order): Single<String> =
        createOrderRepository.sendOrder(order)
}