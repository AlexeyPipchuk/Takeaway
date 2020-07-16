package takeaway.feature_cafe.cafe.ui.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.category_item.view.*
import takeaway.feature_cafe.R
import takeaway.feature_cafe.cafe.presentation.model.CategoryItem

class SelectedCategoryHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createInstance(parent: ViewGroup) = SelectedCategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.selected_category_item,
                parent,
                false
            )
        )
    }

    fun bind(categoryItem: CategoryItem) {
        itemView.categoryName.text = categoryItem.category.title
    }
}