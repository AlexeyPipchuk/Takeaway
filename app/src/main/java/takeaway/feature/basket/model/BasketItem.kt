package takeaway.feature.basket.model

data class BasketItem(
    val productId: String,
    val productName: String,
    val amount: Int,
    val count: Int
)