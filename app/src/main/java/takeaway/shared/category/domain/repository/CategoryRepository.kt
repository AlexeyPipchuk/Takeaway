package takeaway.shared.category.domain.repository

import io.reactivex.Single
import takeaway.shared.category.domain.entity.Category

interface CategoryRepository {

    fun getList(useCache: Boolean): Single<List<Category>>
}