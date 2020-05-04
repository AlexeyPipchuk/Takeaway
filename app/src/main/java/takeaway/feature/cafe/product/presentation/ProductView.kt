package takeaway.feature.cafe.product.presentation

import takeaway.app.TakeawayView
import takeaway.feature.cafe.domain.entity.Product

interface ProductView : TakeawayView {

    fun showProductInfo(product: Product)

    fun incPrice(price: Int)

    fun decPrice(price: Int)

    fun closeProductDialog()

    fun showClearBasketQuestionDialog()
}