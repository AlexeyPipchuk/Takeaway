package takeaway.shared_category.data.model

import com.google.gson.annotations.SerializedName
import takeaway.shared_category.domain.entity.Category

data class CategoryListResponse(
    @SerializedName("items") val categoryList: List<Category>
)