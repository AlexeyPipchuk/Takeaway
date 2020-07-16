package takeaway.feature_cafe.cafe.domain.repository

import domain.entity.Product
import io.reactivex.Single

interface ProductRepository {

    fun getList(cafeId: String): Single<List<Product>>
}