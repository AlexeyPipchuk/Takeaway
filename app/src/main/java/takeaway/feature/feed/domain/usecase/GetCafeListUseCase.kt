package takeaway.feature.feed.domain.usecase

import takeaway.feature.feed.domain.repository.CafeRepository
import takeaway.feature.feed.domain.entity.Cafe
import io.reactivex.Single
import javax.inject.Inject

class GetCafeListUseCase @Inject constructor(
    private val repository: CafeRepository
) {

    operator fun invoke(): Single<List<Cafe>> =
        repository.getList()
            .map {
                it.filter { cafe ->
                    cafe.isVisible
                }
            }
}