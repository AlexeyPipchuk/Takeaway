package takeaway.feature.feed.data.repository

import io.reactivex.Single
import takeaway.feature.feed.data.datasource.CafeDataSource
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.feature.feed.domain.repository.CafeRepository
import javax.inject.Inject

class CafeRepositoryImpl @Inject constructor(
    private val dataSource: CafeDataSource
) : CafeRepository {

    override fun getList(useCache: Boolean): Single<List<Cafe>> =
        if (useCache) {
            val cacheCafeList = dataSource.getListFromCache()
            cacheCafeList?.let { return Single.just(it) } ?: getListFromNetwork()
        } else getListFromNetwork()

    private fun getListFromNetwork(): Single<List<Cafe>> =
        dataSource.getListFromNetwork()
            .map { response ->
                val cafeList = response.popularCafeList.plus(response.cafeList)
                dataSource.setCache(cafeList)
                cafeList
            }
}