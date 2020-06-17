package takeaway.feature.order.registration.domain.repository

import io.reactivex.Single
import takeaway.feature.order.registration.domain.entity.Order

interface CreateOrderRepository {

    fun sendOrder(order: Order): Single<String>
}