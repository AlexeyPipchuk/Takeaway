package takeaway.feature_order_registration.data.datasource

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import takeaway.feature_order_registration.data.network.OrderApi
import takeaway.feature_order_registration.domain.entity.Order
import javax.inject.Inject

class CreateOrderDataSource @Inject constructor(
    private val orderApi: OrderApi
) {

    fun sendOrder(order: Order): Single<String> =
        orderApi.sendOrder(order.cafe!!.name, "")
            .subscribeOn(Schedulers.io())
}