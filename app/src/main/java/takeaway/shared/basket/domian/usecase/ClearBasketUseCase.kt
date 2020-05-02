package takeaway.shared.basket.domian.usecase

import takeaway.shared.basket.domian.repository.BasketRepository
import javax.inject.Inject

class ClearBasketUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    operator fun invoke() {
        repository.clearBasket()
    }
}