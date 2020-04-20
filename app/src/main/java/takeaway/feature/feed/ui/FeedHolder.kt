package takeaway.feature.feed.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.R
import kotlinx.android.synthetic.main.feed_item.view.*
import takeaway.app.fromHtml
import takeaway.app.loadImage
import takeaway.feature.feed.presentation.CafeItem

class FeedHolder(
    view: View,
    private val context: Context
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createInstance(parent: ViewGroup, context: Context) = FeedHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.feed_item,
                parent,
                false
            ),
            context
        )
    }

    fun bind(cafeItem: CafeItem) {
        itemView.cafeName.text = cafeItem.cafeName

        if (cafeItem.deliveryDiscount > 0) {
            itemView.deliveryDiscount.text =
                context.getString(R.string.delivery_discount)
                    .format(cafeItem.deliveryDiscount.toString())
                    .fromHtml()
        }

        itemView.deliveryFreeFrom.text =
            context.getString(R.string.delivery_free_from)
                .format(cafeItem.deliveryFreeFrom.toString())
                .fromHtml()

        if (!cafeItem.imageUrl.isNullOrEmpty()) {
            itemView.cafeImage.loadImage(cafeItem.imageUrl)
        }
        if (!cafeItem.logoUrl.isNullOrEmpty()) {
            itemView.cafeLogo.loadImage(cafeItem.logoUrl)
            itemView.cafeLogo.isVisible = true

        } else {
            itemView.cafeLogo.isVisible = false
        }
    }
}