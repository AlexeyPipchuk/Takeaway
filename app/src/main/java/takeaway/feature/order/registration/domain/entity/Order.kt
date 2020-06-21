package takeaway.feature.order.registration.domain.entity

import takeaway.shared.cafe.domain.entity.Product
import takeaway.shared_cafe.domain.entity.Cafe

data class Order(
    val cafe: Cafe?,
    val productMap: Map<Product, Int>?,

    val name: String,
    val phone: String,
    val email: String,
    val receiveMethod: ReceiveMethod,

    val takeawayAddress: String,
    val takeawayTime: String,

    val street: String,
    val houseNumber: String,
    val porch: String,
    val floor: String,
    val flat: String,
    val comment: String,
    val deliveryTime: String
) {

    companion object {
        val EMPTY = Order(
            cafe = null,
            productMap = null,
            name = "",
            phone = "",
            email = "",
            receiveMethod = ReceiveMethod.TAKEAWAY,
            takeawayAddress = "",
            takeawayTime = "",
            street = "",
            houseNumber = "",
            porch = "",
            floor = "",
            flat = "",
            comment = "",
            deliveryTime = ""
        )
    }
}