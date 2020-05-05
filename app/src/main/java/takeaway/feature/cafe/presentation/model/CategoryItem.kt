package takeaway.feature.cafe.presentation.model

import takeaway.shared.category.domain.entity.Category

data class CategoryItem(
    val category: Category,
    var selected: Boolean = false
)