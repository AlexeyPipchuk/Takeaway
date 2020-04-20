package takeaway.feature.feed.domain.repository

import takeaway.feature.feed.domain.entity.Cafe
import io.reactivex.Single

interface CafeRepository {

    fun getList(): Single<List<Cafe>>
}