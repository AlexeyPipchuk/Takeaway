package takeaway.feature_basket.presentation.model

data class BasketItem(
    val productId: String,
    val productName: String,
    val amount: Int,
    val count: Int
)