package takeaway.feature.feed.data.api

import takeaway.feature.feed.data.model.CafeListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CafeApi {

    @GET("cafe")
    fun getCafeList(@Query("limit") limit: String = "100"): Single<CafeListResponse>
}