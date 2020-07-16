package takeaway.feature_feed.feed.presentation.model

data class CafeItem(
    val cafeName: String,
    val takeawayDiscount: Int,
    val deliveryFreeFrom: Int,
    val imageUrl: String?,
    val logoUrl: String?,
    val isPopular: Boolean
) : FeedItem