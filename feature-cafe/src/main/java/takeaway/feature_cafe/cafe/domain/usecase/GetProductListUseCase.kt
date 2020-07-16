package takeaway.feature_cafe.cafe.domain.usecase

import domain.entity.Product
import io.reactivex.Single
import takeaway.feature_cafe.cafe.domain.repository.ProductRepository
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