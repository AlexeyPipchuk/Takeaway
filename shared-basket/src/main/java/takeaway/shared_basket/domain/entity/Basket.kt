package takeaway.shared_basket.domain.entity

import domain.entity.Product
import takeaway.shared_cafe.domain.entity.Cafe

data class Basket(
    val cafe: Cafe?,
    val products: Map<Product, Int>?
)