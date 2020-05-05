package takeaway.shared.cafe.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import takeaway.shared.cafe.data.model.ProductListResponse

interface ProductApi {

    @GET("products?category_id=&")
    fun getProductList(
        @Query("cafe_id") cafeId: String,
        @Query("limit") limit: String = "200"
    ): Single<ProductListResponse>
}