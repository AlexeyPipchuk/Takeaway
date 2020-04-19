package com.example.takeaway.feature.feed.data.api

import com.example.takeaway.feature.feed.data.model.CafeListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TakeawayApi {

    @GET("cafe")
    fun getCafeList(@Query("limit") limit: String = "100"): Single<CafeListResponse>
}