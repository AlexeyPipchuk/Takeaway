package takeaway.feature.cafe.ui.product

import takeaway.app.TakeawayView
import takeaway.feature.cafe.domain.entity.Product

interface ProductView : TakeawayView {

    fun showProductInfo(product: Product)
}