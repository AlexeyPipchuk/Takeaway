package takeaway.feature_cafe.cafe.ui.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.category_item.view.*
import takeaway.feature_cafe.R
import takeaway.feature_cafe.cafe.presentation.model.CategoryItem

class CategoryHolder(
    view: View,
    private val onCategoryClickListener: (CategoryItem) -> Unit
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createInstance(
            parent: ViewGroup,
            onCategoryClickListener: (CategoryItem) -> Unit
        ) = CategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.category_item,
                parent,
                false
            ),
            onCategoryClickListener
        )
    }

    private lateinit var item: CategoryItem

    init {
        itemView.setOnClickListener { onCategoryClickListener(item) }
    }

    fun bind(categoryItem: CategoryItem) {
        this.item = categoryItem

        itemView.categoryName.text = categoryItem.category.title
    }
}