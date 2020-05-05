package takeaway.shared.category.data.model

import com.google.gson.annotations.SerializedName
import takeaway.shared.category.domain.entity.Category

data class CategoryListResponse(
    @SerializedName("items") val categoryList: List<Category>
)