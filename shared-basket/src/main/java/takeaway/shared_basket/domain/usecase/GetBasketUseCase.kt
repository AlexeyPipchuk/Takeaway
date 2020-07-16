package takeaway.shared_basket.domain.usecase

import takeaway.shared_basket.domain.entity.Basket
import takeaway.shared_basket.domain.repository.BasketRepository
import javax.inject.Inject

class GetBasketUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    operator fun invoke(): Basket =
        repository.getBasket()
}