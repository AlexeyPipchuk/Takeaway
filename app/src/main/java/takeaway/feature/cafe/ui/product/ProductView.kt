package takeaway.feature.cafe.ui.product

import takeaway.app.TakeawayView
import takeaway.feature.cafe.domain.entity.Product

interface ProductView : TakeawayView {

    fun showProductInfo(product: Product)

    fun incPrice(price: Int)

    fun decPrice(price: Int)

    fun closeProductDialog()

    fun showClearBasketQuestionDialog()
}