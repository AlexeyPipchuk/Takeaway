package takeaway.feature.feed.data.datasource

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import takeaway.feature.feed.data.api.CafeApi
import takeaway.feature.feed.data.model.CafeListResponse
import takeaway.feature.feed.domain.entity.Cafe
import javax.inject.Inject

class CafeDataSource @Inject constructor(
    private val api: CafeApi
) {

    private var cacheCafeList: List<Cafe>? = null

    fun getListFromNetwork(): Single<CafeListResponse> =
        api.getCafeList()
            .subscribeOn(Schedulers.io())

    fun getListFromCache(): List<Cafe>? = cacheCafeList

    fun setCache(cafeList: List<Cafe>) {
        cacheCafeList = cafeList
    }
}