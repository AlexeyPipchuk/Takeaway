package takeaway.feature.feed.presentation

import takeaway.feature.feed.ui.holder.FeedItem

data class CafeItem(
    val cafeName: String,
    val takeawayDiscount: Int,
    val deliveryFreeFrom: Int,
    val imageUrl: String?,
    val logoUrl: String?,
    val isPopular: Boolean
) : FeedItem