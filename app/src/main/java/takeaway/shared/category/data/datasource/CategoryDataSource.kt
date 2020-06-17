package takeaway.shared.category.data.datasource

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import takeaway.shared.category.data.api.CategoryApi
import takeaway.shared.category.data.model.CategoryListResponse
import takeaway.shared.category.domain.entity.Category
import javax.inject.Inject

class CategoryDataSource @Inject constructor(
    private val api: CategoryApi
) {

    private var cacheCategoryList: List<Category>? = null

    fun getListFromNetwork(): Single<CategoryListResponse> =
        api.getCategoryList()
            .subscribeOn(Schedulers.io())

    fun getListFromCache(): List<Category>? = cacheCategoryList

    fun setCache(categoryList: List<Category>) {
        cacheCategoryList = categoryList
    }
}