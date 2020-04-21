package takeaway.feature.feed.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.feature.feed.presentation.CafeItem

class FeedAdapter(
    private val context: Context,
    private val onCafeClickListener: (CafeItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var cafeList: List<CafeItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        FeedHolder.createInstance(parent, context, onCafeClickListener)

    override fun getItemCount(): Int = cafeList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FeedHolder) {
            holder.bind(cafeList[position])
        }
    }
}