package takeaway.feature_feed.feed.ui.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.separator_item.view.*
import takeaway.feature_feed.R
import takeaway.feature_feed.feed.presentation.model.Separator

class SeparatorHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createInstance(
            parent: ViewGroup
        ) = SeparatorHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.separator_item,
                parent,
                false
            )
        )
    }

    fun bind(separator: Separator) {
        itemView.separatorTitle.text = separator.title
    }
}