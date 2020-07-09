package takeaway.feature.cafe.product.ui.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.R
import domain.entity.Product
import kotlinx.android.synthetic.main.product_item.view.*
import takeaway.app.fromHtml
import takeaway.app.loadImage

class ProductHolder(
    view: View,
    private val context: Context,
    private val onCafeClickListener: (Product) -> Unit
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createInstance(
            parent: ViewGroup,
            context: Context,
            onCafeClickListener: (Product) -> Unit
        ) = ProductHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.product_item,
                parent,
                false
            ),
            context,
            onCafeClickListener
        )
    }

    private lateinit var item: Product

    init {
        itemView.setOnClickListener { onCafeClickListener(item) }
    }

    fun bind(productItem: Product) {
        this.item = productItem

        productItem.imgUrl?.let { url ->
            if (url.isNotEmpty()) {
                itemView.productImg.loadImage(url)
            }
        }

        itemView.productName.text = productItem.title
        itemView.productDescription.text = productItem.description
        itemView.productWeight.text =
            context.getString(R.string.product_weight).format(productItem.weight)
        itemView.productPrice.text = context.getString(R.string.product_price)
            .format(productItem.price)
            .fromHtml()
    }
}