package domain.entity

import takeaway.shared_cafe.domain.entity.Cafe
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