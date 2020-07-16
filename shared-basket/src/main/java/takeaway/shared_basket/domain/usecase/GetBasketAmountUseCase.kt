package takeaway.shared_basket.domain.usecase

import takeaway.shared_basket.domain.repository.BasketRepository
import javax.inject.Inject

class GetBasketAmountUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    operator fun invoke(): Int =
        repository.getBasketAmount()
}