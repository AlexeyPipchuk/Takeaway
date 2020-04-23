package takeaway.feature.cafe.domain.usecase

import io.reactivex.Single
import takeaway.feature.cafe.domain.entity.Product
import takeaway.feature.cafe.domain.repository.ProductRepository
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