package takeaway.feature.cafe.ui.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.R
import kotlinx.android.synthetic.main.category_item.view.*
import takeaway.feature.cafe.presentation.model.CategoryItem

class SelectedCategoryHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createInstance(
            parent: ViewGroup
        ) = SelectedCategoryHolder(
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