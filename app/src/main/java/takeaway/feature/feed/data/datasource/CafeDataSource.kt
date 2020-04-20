package com.example.takeaway.feature.feed.data.datasource

import com.example.takeaway.feature.feed.data.api.TakeawayApi
import com.example.takeaway.feature.feed.data.model.CafeListResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface CafeDataSource {

    fun getList(): Single<CafeListResponse>
}

class CafeDataSourceImpl @Inject constructor(
    private val api: TakeawayApi
) : CafeDataSource {

    override fun getList(): Single<CafeListResponse> =
        api.getCafeList()
            .subscribeOn(Schedulers.io())
}