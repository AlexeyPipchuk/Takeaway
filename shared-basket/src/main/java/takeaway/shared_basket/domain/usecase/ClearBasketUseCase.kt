package takeaway.shared_basket.domain.usecase

import takeaway.shared_basket.domain.repository.BasketRepository
import javax.inject.Inject

class ClearBasketUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    operator fun invoke() {
        repository.clearBasket()
    }
}