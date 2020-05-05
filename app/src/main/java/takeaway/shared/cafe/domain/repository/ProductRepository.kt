package takeaway.shared.cafe.domain.repository

import io.reactivex.Single
import takeaway.shared.cafe.domain.entity.Product

interface ProductRepository {

    fun getList(cafeId: String): Single<List<Product>>
}