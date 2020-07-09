package takeaway.feature.cafe.product.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import takeaway.feature.cafe.product.ui.holder.ProductHolder

class ProductAdapter(
    private val context: Context,
    private val onCafeClickListener: (domain.entity.Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var productList: List<domain.entity.Product> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ProductHolder.createInstance(
            parent,
            context,
            onCafeClickListener
        )

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductHolder) {
            holder.bind(productList[position])
        }
    }
}