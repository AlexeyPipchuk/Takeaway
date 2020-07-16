package takeaway.feature_order_registration.domain.repository

import io.reactivex.Single
import takeaway.feature_order_registration.domain.entity.Order

interface CreateOrderRepository {

    fun sendOrder(order: Order): Single<String>
}