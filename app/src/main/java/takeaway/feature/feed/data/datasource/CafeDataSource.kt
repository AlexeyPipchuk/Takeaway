package takeaway.feature.feed.data.datasource

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import takeaway.feature.feed.data.api.CafeApi
import takeaway.feature.feed.data.model.CafeListResponse
import takeaway.feature.feed.domain.entity.Cafe
import javax.inject.Inject

interface CafeDataSource {

    fun getListFromNetwork(): Single<CafeListResponse>

    fun getListFromCache(): List<Cafe>?

    fun setCache(cafeList: List<Cafe>)
}

class CafeDataSourceImpl @Inject constructor(
    private val api: CafeApi
) : CafeDataSource {

    private var cacheCafeList: List<Cafe>? = null

    override fun getListFromNetwork(): Single<CafeListResponse> =
        api.getCafeList()
            .subscribeOn(Schedulers.io())

    override fun getListFromCache(): List<Cafe>? = cacheCafeList

    override fun setCache(cafeList: List<Cafe>) {
        cacheCafeList = cafeList
    }
}