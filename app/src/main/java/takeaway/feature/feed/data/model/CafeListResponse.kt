package takeaway.feature.feed.data.model

import takeaway.feature.feed.domain.entity.Cafe
import com.google.gson.annotations.SerializedName

data class CafeListResponse(
    @SerializedName("items") val cafeList: List<Cafe>
)