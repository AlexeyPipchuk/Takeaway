package takeaway.feature.feed.data.datasource

import takeaway.feature.feed.data.api.CafeApi
import takeaway.feature.feed.data.model.CafeListResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface CafeDataSource {

    fun getList(): Single<CafeListResponse>
}

class CafeDataSourceImpl @Inject constructor(
    private val api: CafeApi
) : CafeDataSource {

    override fun getList(): Single<CafeListResponse> =
        api.getCafeList()
            .subscribeOn(Schedulers.io())
}