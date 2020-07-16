package takeaway.feature_cafe.product.presentation

import base.TakeawayView
import domain.entity.Product

interface ProductView : TakeawayView {

    fun showProductInfo(product: Product)

    fun incPrice(price: Int)

    fun decPrice(price: Int)

    fun closeProductDialog()

    fun showClearBasketQuestionDialog()
}