package takeaway.shared_cafe.data.model

import com.google.gson.annotations.SerializedName
import takeaway.shared_cafe.domain.entity.Cafe

data class CafeListResponse(
    @SerializedName("items") val cafeList: List<Cafe>,
    @SerializedName("popular") val popularCafeList: List<Cafe>
)