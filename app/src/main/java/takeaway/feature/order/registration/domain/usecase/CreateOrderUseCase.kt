package takeaway.feature.order.registration.domain.usecase

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import takeaway.feature.order.registration.domain.entity.Order
import javax.inject.Inject

//TODO(Заглушка)
class CreateOrderUseCase @Inject constructor() {

    operator fun invoke(order: Order): Single<String> =
        Single.just("003")
            .subscribeOn(Schedulers.io())
}