package takeaway.feature.feed.ui.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.R

class PromoHolder(
    view: View,
    private val onPromoClickListener: () -> Unit
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createInstance(
            parent: ViewGroup,
            onPromoLinkListener: () -> Unit
        ) = PromoHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.promo_item,
                parent,
                false
            ),
            onPromoLinkListener
        )
    }

    init {
        itemView.setOnClickListener { onPromoClickListener() }
    }
}