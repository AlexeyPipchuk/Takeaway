package takeaway.feature.cafe.product.presentation

import takeaway.app.TakeawayView

interface ProductView : TakeawayView {

    fun showProductInfo(product: domain.entity.Product)

    fun incPrice(price: Int)

    fun decPrice(price: Int)

    fun closeProductDialog()

    fun showClearBasketQuestionDialog()
}