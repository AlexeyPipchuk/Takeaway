package takeaway.shared.order.registration.domain.entity

import takeaway.feature.feed.domain.entity.Cafe
import takeaway.shared.cafe.domain.entity.Product
import java.io.Serializable

data class OrderSketch(
    val cafe: Cafe,
    val basketAmountWithoutAll: Int,
    val products: Map<Product, Int>,
    val takeawayDiscountCalculated: Int,
    val orderWithTakeawayAmount: Int,
    val deliveryCostCalculated: Int,
    val orderWithDeliveryAmount: Int,
    val valueToFreeDelivery: Int,
    val deliveryAllowed: Boolean
) : Serializable