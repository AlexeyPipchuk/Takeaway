package takeaway.feature.feed.ui.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.R
import kotlinx.android.synthetic.main.separator_item.view.*
import takeaway.feature.feed.presentation.Separator

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