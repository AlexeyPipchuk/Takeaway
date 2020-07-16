package takeaway.feature.feed.ui.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.R
import kotlinx.android.synthetic.main.cafe_item.view.*
import takeaway.component_ui.extensions.loadImage
import takeaway.component_ui.extensions.fromHtml
import takeaway.feature.feed.presentation.model.CafeItem

class CafeHolder(
    view: View,
    private val context: Context,
    private val onCafeClickListener: (CafeItem) -> Unit
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createInstance(
            parent: ViewGroup,
            context: Context,
            onCafeClickListener: (CafeItem) -> Unit
        ) = CafeHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cafe_item,
                parent,
                false
            ),
            context,
            onCafeClickListener
        )
    }

    private lateinit var item: CafeItem

    init {
        itemView.setOnClickListener { onCafeClickListener(item) }
    }

    fun bind(cafeItem: CafeItem) {
        this.item = cafeItem

        itemView.cafeName.text = cafeItem.cafeName

        if (cafeItem.takeawayDiscount > 0) {
            itemView.deliveryDiscount.text =
                context.getString(R.string.takeaway_discount_colored)
                    .format(cafeItem.takeawayDiscount.toString())
                    .fromHtml()
        }

        itemView.deliveryFreeFrom.text =
            context.getString(R.string.delivery_free_from_colored)
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