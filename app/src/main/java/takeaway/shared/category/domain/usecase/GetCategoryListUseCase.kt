package takeaway.shared.category.domain.usecase

import io.reactivex.Single
import takeaway.shared.category.domain.entity.Category
import takeaway.shared.category.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    operator fun invoke(useCache: Boolean = false): Single<List<Category>> =
        repository.getList(useCache)
}