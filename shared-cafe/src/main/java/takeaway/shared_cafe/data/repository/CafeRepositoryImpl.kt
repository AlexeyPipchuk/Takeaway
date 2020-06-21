package takeaway.shared_cafe.data.repository

import io.reactivex.Single
import takeaway.shared_cafe.data.datasource.CafeDataSource
import takeaway.shared_cafe.domain.entity.Cafe
import takeaway.shared_cafe.domain.repository.CafeRepository
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