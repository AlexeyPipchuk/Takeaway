package takeaway.shared.basket.domian.usecase

import takeaway.shared.basket.domian.entity.Basket
import takeaway.shared.basket.domian.repository.BasketRepository
import javax.inject.Inject

class GetBasketUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    operator fun invoke(): Basket? =
        repository.getBasket()
}