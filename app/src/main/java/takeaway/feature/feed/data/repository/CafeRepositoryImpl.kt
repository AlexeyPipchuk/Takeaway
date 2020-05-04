package takeaway.feature.feed.data.repository

import io.reactivex.Single
import takeaway.feature.feed.data.datasource.CafeDataSource
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.feature.feed.domain.repository.CafeRepository
import javax.inject.Inject

class CafeRepositoryImpl @Inject constructor(
    private val dataSource: CafeDataSource
) : CafeRepository {

    override fun getList(): Single<List<Cafe>> =
        dataSource.getList()
            .map { response ->
                response.popularCafeList.plus(response.cafeList)
            }
}