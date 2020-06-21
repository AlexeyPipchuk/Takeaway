package takeaway.shared_cafe.data.datasource

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import takeaway.shared_cafe.data.api.CafeApi
import takeaway.shared_cafe.data.model.CafeListResponse
import takeaway.shared_cafe.domain.entity.Cafe
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