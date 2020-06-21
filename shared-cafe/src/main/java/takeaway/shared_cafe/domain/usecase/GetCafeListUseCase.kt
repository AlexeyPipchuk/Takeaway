package takeaway.shared_cafe.domain.usecase

import io.reactivex.Single
import takeaway.shared_cafe.domain.entity.Cafe
import takeaway.shared_cafe.domain.repository.CafeRepository
import javax.inject.Inject

class GetCafeListUseCase @Inject constructor(
    private val repository: CafeRepository
) {

    operator fun invoke(useCache: Boolean = false): Single<List<Cafe>> =
        repository.getList(useCache)
            .map {
                it.filter { cafe ->
                    cafe.isVisible && !cafe.isClosed
                }
            }
}