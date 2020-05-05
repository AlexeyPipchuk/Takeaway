package takeaway.shared.cafe.domain.usecase

import io.reactivex.Single
import takeaway.shared.cafe.domain.entity.Product
import takeaway.shared.cafe.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    operator fun invoke(cafeId: String): Single<List<Product>> =
        repository.getList(cafeId)
            .map {
                it.filter { product ->
                    product.isVisible
                }
            }
}