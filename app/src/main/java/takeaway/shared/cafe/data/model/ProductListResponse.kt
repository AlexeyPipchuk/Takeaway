package takeaway.shared.cafe.data.model

import com.google.gson.annotations.SerializedName
import takeaway.shared.cafe.domain.entity.Product

//TODO(Подумать над baseResponse)
data class ProductListResponse(
    @SerializedName("items") val productList: List<Product>
)