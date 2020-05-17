package takeaway.feature.order.registration.domain.usecase

import takeaway.feature.order.registration.domain.entity.Order
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor() {

    operator fun invoke(order: Order) {
        //TODO(DATA слой регистрации ордера)
    }
}