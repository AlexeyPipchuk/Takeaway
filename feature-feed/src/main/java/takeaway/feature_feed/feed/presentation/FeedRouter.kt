package takeaway.feature_feed.feed.presentation

import takeaway.shared_cafe.domain.entity.Cafe

interface FeedRouter {

    fun toErrorScreen()

    fun toInfoScreen()

    fun toAddNewCafeScreen()

    fun toCafeScreen(cafe: Cafe)
}