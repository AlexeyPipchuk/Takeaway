package takeaway.shared.cafe.data.model

import com.google.gson.annotations.SerializedName
import takeaway.shared.cafe.domain.entity.Product

data class ProductListResponse(
    @SerializedName("items") val productList: List<Product>
)