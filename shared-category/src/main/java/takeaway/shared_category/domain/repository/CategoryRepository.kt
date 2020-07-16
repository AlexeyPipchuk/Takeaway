package takeaway.shared_category.domain.repository

import io.reactivex.Single
import takeaway.shared_category.domain.entity.Category

interface CategoryRepository {

    fun getList(useCache: Boolean): Single<List<Category>>
}