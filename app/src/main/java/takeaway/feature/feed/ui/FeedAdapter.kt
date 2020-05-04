package takeaway.feature.feed.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import takeaway.feature.feed.presentation.CafeItem
import takeaway.feature.feed.presentation.Separator
import takeaway.feature.feed.ui.holder.CafeHolder
import takeaway.feature.feed.ui.holder.FeedItem
import takeaway.feature.feed.ui.holder.SeparatorHolder

class FeedAdapter(
    private val context: Context,
    private val onCafeClickListener: (CafeItem) -> Unit
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
            else -> throw  IllegalArgumentException("no such item for viewType $viewType")
        }

    override fun getItemCount(): Int = feedItems.size

    override fun getItemViewType(position: Int): Int =
        when (feedItems[position]) {
            is CafeItem -> FeedItemType.CAFE_ITEM.ordinal
            is Separator -> FeedItemType.SEPARATOR_ITEM.ordinal
            else -> throw  IllegalArgumentException("no viewHolder for type ${feedItems[position].javaClass}")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CafeHolder -> holder.bind(feedItems[position] as CafeItem)
            is SeparatorHolder -> holder.bind(feedItems[position] as Separator)
        }
    }

    enum class FeedItemType {
        CAFE_ITEM,
        SEPARATOR_ITEM
    }
}