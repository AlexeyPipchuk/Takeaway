package takeaway.feature.basket.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import takeaway.feature.basket.model.BasketItem
import takeaway.feature.basket.ui.holder.BasketHolder

class BasketAdapter(
    private val context: Context,
    private val onProductDeleteClickListener: (BasketItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var basketItems: List<BasketItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        BasketHolder.createInstance(parent, context, onProductDeleteClickListener)

    override fun getItemCount(): Int = basketItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BasketHolder) {
            holder.bind(basketItems[position])
        }
    }
}