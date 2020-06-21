package takeaway.shared.basket.domian.entity

import takeaway.shared.cafe.domain.entity.Product
import takeaway.shared_cafe.domain.entity.Cafe

data class Basket(
    val cafe: Cafe?,
    val products: Map<Product, Int>?
)