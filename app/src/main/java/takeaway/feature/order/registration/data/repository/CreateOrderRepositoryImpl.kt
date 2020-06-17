package takeaway.feature.order.registration.data.repository

import io.reactivex.Single
import takeaway.feature.order.registration.data.datasource.CreateOrderDataSource
import takeaway.feature.order.registration.domain.entity.Order
import takeaway.feature.order.registration.domain.repository.CreateOrderRepository
import javax.inject.Inject

class CreateOrderRepositoryImpl @Inject constructor(
    private val createOrderDataSource: CreateOrderDataSource
) : CreateOrderRepository {

    override fun sendOrder(order: Order): Single<String> =
        createOrderDataSource.sendOrder(order)
}