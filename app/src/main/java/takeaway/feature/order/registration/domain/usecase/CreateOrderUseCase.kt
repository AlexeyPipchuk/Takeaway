package takeaway.feature.order.registration.domain.usecase

import io.reactivex.Single
import takeaway.feature.order.registration.domain.entity.Order
import takeaway.feature.order.registration.domain.repository.CreateOrderRepository
import javax.inject.Inject

//TODO(Заглушка)
class CreateOrderUseCase @Inject constructor(
    private val createOrderRepository: CreateOrderRepository
) {

    operator fun invoke(order: Order): Single<String> =
        createOrderRepository.sendOrder(order)
}