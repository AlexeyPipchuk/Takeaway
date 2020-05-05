package takeaway.shared.basket.domian.usecase

import takeaway.shared.cafe.domain.entity.Product
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.shared.basket.domian.repository.BasketRepository
import javax.inject.Inject

class AddToBasketUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    operator fun invoke(product: Product, count: Int, cafe: Cafe) {
        repository.addToBasket(product, count, cafe)
    }
}