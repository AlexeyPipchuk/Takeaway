package takeaway.feature_cafe.cafe.data.model

import com.google.gson.annotations.SerializedName
import domain.entity.Product

data class ProductListResponse(
    @SerializedName("items") val productList: List<Product>
)