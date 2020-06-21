package takeaway.shared_cafe.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import takeaway.shared_cafe.data.model.CafeListResponse

interface CafeApi {

    @GET("cafe")
    fun getCafeList(@Query("limit") limit: String = "100"): Single<CafeListResponse>
}