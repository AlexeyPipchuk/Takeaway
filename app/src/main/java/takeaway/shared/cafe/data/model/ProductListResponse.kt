package takeaway.shared.cafe.data.model

import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("items") val productList: List<domain.entity.Product>
)