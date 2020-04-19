package com.example.takeaway.feature.feed.data.api

import com.example.takeaway.feature.feed.data.model.CafeListResponse
import io.reactivex.Single
import retrofit2.http.GET

interface TakeawayApi {

    @GET("cafe?type=closed")
    fun getCafeList(): Single<CafeListResponse>
}