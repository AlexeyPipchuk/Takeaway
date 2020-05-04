package takeaway.feature.feed.domain.repository

import io.reactivex.Single
import takeaway.feature.feed.domain.entity.Cafe

interface CafeRepository {

    fun getList(useCache: Boolean): Single<List<Cafe>>
}