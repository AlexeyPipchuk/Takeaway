package takeaway.shared.category.data.datasource

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import takeaway.shared.category.data.api.CategoryApi
import takeaway.shared.category.data.model.CategoryListResponse
import takeaway.shared.category.domain.entity.Category
import javax.inject.Inject

interface CategoryDataSource {

    fun getListFromNetwork(): Single<CategoryListResponse>

    fun getListFromCache(): List<Category>?

    fun setCache(categoryList: List<Category>)
}

class CategoryDataSourceImpl @Inject constructor(
    private val api: CategoryApi
) : CategoryDataSource {

    private var cacheCategoryList: List<Category>? = null

    override fun getListFromNetwork(): Single<CategoryListResponse> =
        api.getCategoryList()
            .subscribeOn(Schedulers.io())

    override fun getListFromCache(): List<Category>? = cacheCategoryList

    override fun setCache(categoryList: List<Category>) {
        cacheCategoryList = categoryList
    }
}