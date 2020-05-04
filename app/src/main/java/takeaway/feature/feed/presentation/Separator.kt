package takeaway.feature.feed.presentation

import takeaway.feature.feed.ui.holder.FeedItem

data class Separator(val title: String) : FeedItem {

    companion object {
        val POPULAR = Separator("Популярное")
        val NOT_POPULAR = Separator("Заведения")
    }
}