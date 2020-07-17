package takeaway.shared_category.domain.usecase

import io.reactivex.Single
import takeaway.shared_category.domain.entity.Category
import takeaway.shared_category.domain.repository.CategoryRepository
import javax.inject.Inject

class GetAllCategoryListUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    operator fun invoke(useCache: Boolean = false): Single<List<Category>> =
        repository.getList(useCache)
}