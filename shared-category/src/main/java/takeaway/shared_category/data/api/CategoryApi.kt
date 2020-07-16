package takeaway.shared_category.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import takeaway.shared_category.data.model.CategoryListResponse

interface CategoryApi {

    @GET("category")
    fun getCategoryList(
        @Query("limit") limit: String = "300"
    ): Single<CategoryListResponse>
}