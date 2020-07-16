package takeaway.feature_cafe.cafe.presentation.model

import takeaway.shared_category.domain.entity.Category

data class CategoryItem(
    val category: Category,
    var selected: Boolean = false
)