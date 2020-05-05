package takeaway.shared.category.domain.usecase

import io.reactivex.Single
import takeaway.shared.category.domain.entity.Category
import takeaway.shared.category.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    operator fun invoke(
        useCache: Boolean = false,
        cafeCategories: List<Int>? = null
    ): Single<List<Category>> =
        repository.getList(useCache)
            .map { categories ->
                cafeCategories?.let {
                    categories.filter { cafeCategories.contains(it.id) }
                }
            }
}