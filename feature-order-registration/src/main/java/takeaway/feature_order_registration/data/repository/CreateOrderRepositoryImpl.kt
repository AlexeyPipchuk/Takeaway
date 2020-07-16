package takeaway.feature_order_registration.data.repository

import io.reactivex.Single
import takeaway.feature_order_registration.data.datasource.CreateOrderDataSource
import takeaway.feature_order_registration.domain.entity.Order
import takeaway.feature_order_registration.domain.repository.CreateOrderRepository
import javax.inject.Inject

class CreateOrderRepositoryImpl @Inject constructor(
    private val createOrderDataSource: CreateOrderDataSource
) : CreateOrderRepository {

    override fun sendOrder(order: Order): Single<String> =
        createOrderDataSource.sendOrder(order)
}