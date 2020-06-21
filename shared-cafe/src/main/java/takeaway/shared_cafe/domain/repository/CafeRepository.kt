package takeaway.shared_cafe.domain.repository

import io.reactivex.Single
import takeaway.shared_cafe.domain.entity.Cafe

interface CafeRepository {

    fun getList(useCache: Boolean): Single<List<Cafe>>
}