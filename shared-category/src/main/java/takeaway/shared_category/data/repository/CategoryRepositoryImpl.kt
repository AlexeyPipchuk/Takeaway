package takeaway.shared_category.data.repository

import io.reactivex.Single
import takeaway.shared_category.data.datasource.CategoryDataSource
import takeaway.shared_category.domain.entity.Category
import takeaway.shared_category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dataSource: CategoryDataSource
) : CategoryRepository {

    override fun getList(useCache: Boolean): Single<List<Category>> =
        if (useCache) {
            val cacheCategoryList = dataSource.getListFromCache()
            cacheCategoryList?.let { return Single.just(it) } ?: getListFromNetwork()
        } else getListFromNetwork()

    private fun getListFromNetwork(): Single<List<Category>> =
        dataSource.getListFromNetwork()
            .map { response ->
                val categoryList = response.categoryList
                dataSource.setCache(categoryList)
                categoryList
            }
}