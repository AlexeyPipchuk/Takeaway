package takeaway.feature_cafe.cafe.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import takeaway.feature_cafe.cafe.presentation.model.CategoryItem
import takeaway.feature_cafe.cafe.ui.holder.CategoryHolder
import takeaway.feature_cafe.cafe.ui.holder.SelectedCategoryHolder

class CategoryAdapter(
    private val onCategoryClickListener: (CategoryItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var categoryList: List<CategoryItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            CategorySelected.SELECTED.ordinal -> SelectedCategoryHolder.createInstance(parent)
            CategorySelected.NOT_SELECTED.ordinal -> CategoryHolder.createInstance(
                parent,
                onCategoryClickListener
            )
            else -> throw  IllegalArgumentException("no such item for viewType $viewType")
        }

    override fun getItemCount(): Int = categoryList.size

    override fun getItemViewType(position: Int): Int =
        if (categoryList[position].selected) {
            CategorySelected.SELECTED.ordinal
        } else CategorySelected.NOT_SELECTED.ordinal

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryHolder -> holder.bind(categoryList[position])
            is SelectedCategoryHolder -> holder.bind(categoryList[position])
        }
    }

    enum class CategorySelected {
        SELECTED,
        NOT_SELECTED
    }
}