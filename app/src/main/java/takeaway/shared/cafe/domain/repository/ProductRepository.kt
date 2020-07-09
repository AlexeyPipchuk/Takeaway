package takeaway.shared.cafe.domain.repository

import io.reactivex.Single

interface ProductRepository {

    fun getList(cafeId: String): Single<List<domain.entity.Product>>
}