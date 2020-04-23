package takeaway.feature.cafe.data.model

import com.google.gson.annotations.SerializedName
import takeaway.feature.cafe.domain.entity.Product

//TODO(Подумать над baseResponse)
data class ProductListResponse(
    @SerializedName("items") val productList: List<Product>
)