package takeaway.feature.feed.presentation

data class CafeItem (
    val cafeName: String,
    val deliveryDiscount: Int,
    val deliveryFreeFrom: Int,
    val imageUrl: String?,
    val logoUrl: String?
)