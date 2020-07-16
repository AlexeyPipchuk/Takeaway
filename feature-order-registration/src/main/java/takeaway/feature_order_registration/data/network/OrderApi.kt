package takeaway.feature_order_registration.data.network

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OrderApi {

    @FormUrlEncoded
    @POST("order")
    fun sendOrder(
        @Field("cafe") cafe: String,
        @Field("test") test: String
    ): Single<String>
}