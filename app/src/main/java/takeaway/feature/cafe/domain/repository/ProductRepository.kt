package takeaway.feature.cafe.domain.repository

import io.reactivex.Single
import takeaway.feature.cafe.domain.entity.Product

interface ProductRepository {

    fun getList(cafeId: String): Single<List<Product>>
}