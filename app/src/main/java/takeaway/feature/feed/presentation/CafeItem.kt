package takeaway.feature.feed.presentation

data class CafeItem (
    val cafeName: String,
    val takeawayDiscount: Int,
    val deliveryFreeFrom: Int,
    val imageUrl: String?,
    val logoUrl: String?
)