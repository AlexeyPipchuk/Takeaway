package takeaway.feature_basket.ui.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.basket_item.view.*
import takeaway.feature_basket.R
import takeaway.feature_basket.presentation.model.BasketItem

class BasketHolder(
    view: View,
    private val context: Context,
    private val onProductDeleteClickListener: (BasketItem) -> Unit
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createInstance(
            parent: ViewGroup,
            context: Context,
            onProductDeleteClickListener: (BasketItem) -> Unit
        ) = BasketHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.basket_item,
                parent,
                false
            ),
            context,
            onProductDeleteClickListener
        )
    }

    private lateinit var item: BasketItem

    init {
        itemView.deleteProductButton.setOnClickListener { onProductDeleteClickListener(item) }
    }

    fun bind(basketItem: BasketItem) {
        this.item = basketItem

        itemView.productName.text = basketItem.productName
        itemView.amountPrice.text = context.getString(R.string.rubles_postfix)
            .format(basketItem.amount)
        itemView.count.text = context.getString(R.string.product_count)
            .format(basketItem.count)
    }
}