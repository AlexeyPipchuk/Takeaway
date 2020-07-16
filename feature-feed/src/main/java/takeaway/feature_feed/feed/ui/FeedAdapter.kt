package takeaway.feature_feed.feed.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import takeaway.feature_feed.feed.presentation.model.CafeItem
import takeaway.feature_feed.feed.presentation.model.FeedItem
import takeaway.feature_feed.feed.presentation.model.Promo
import takeaway.feature_feed.feed.presentation.model.Separator
import takeaway.feature_feed.feed.ui.holder.CafeHolder
import takeaway.feature_feed.feed.ui.holder.PromoHolder
import takeaway.feature_feed.feed.ui.holder.SeparatorHolder

class FeedAdapter(
    private val context: Context,
    private val onCafeClickListener: (CafeItem) -> Unit,
    private val onPromoClickListener: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var feedItems: List<FeedItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            FeedItemType.CAFE_ITEM.ordinal -> CafeHolder.createInstance(
                parent,
                context,
                onCafeClickListener
            )
            FeedItemType.SEPARATOR_ITEM.ordinal -> SeparatorHolder.createInstance(parent)
            FeedItemType.PROMO_ITEM.ordinal -> PromoHolder.createInstance(
                parent,
                onPromoClickListener
            )
            else -> throw  IllegalArgumentException("no such item for viewType $viewType")
        }

    override fun getItemCount(): Int = feedItems.size

    override fun getItemViewType(position: Int): Int =
        when (feedItems[position]) {
            is CafeItem -> FeedItemType.CAFE_ITEM.ordinal
            is Separator -> FeedItemType.SEPARATOR_ITEM.ordinal
            is Promo -> FeedItemType.PROMO_ITEM.ordinal
            else -> throw  IllegalArgumentException("no viewHolder for type ${feedItems[position].javaClass}")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CafeHolder -> holder.bind(feedItems[position] as CafeItem)
            is SeparatorHolder -> holder.bind(feedItems[position] as Separator)
            is PromoHolder -> Unit
        }
    }

    enum class FeedItemType {
        CAFE_ITEM,
        SEPARATOR_ITEM,
        PROMO_ITEM
    }
}