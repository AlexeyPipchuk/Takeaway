package takeaway.feature.feed.presentation.model

data class Separator(val title: String) :
    FeedItem {

    companion object {
        val POPULAR = Separator("Популярное")
        val NOT_POPULAR = Separator("Заведения")
    }
}