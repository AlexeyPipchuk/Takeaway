package takeaway.feature.cafe.presentation.model

import takeaway.shared_category.domain.entity.Category

data class CategoryItem(
    val category: takeaway.shared_category.domain.entity.Category,
    var selected: Boolean = false
)