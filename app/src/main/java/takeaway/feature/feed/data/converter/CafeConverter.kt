package takeaway.feature.feed.data.converter

import takeaway.feature.feed.data.model.CafeListResponse
import takeaway.feature.feed.domain.entity.Cafe
import javax.inject.Inject

class CafeConverter @Inject constructor() {

    fun toCafeList(cafeListResponse: CafeListResponse): List<Cafe> =
        cafeListResponse.cafeList
}