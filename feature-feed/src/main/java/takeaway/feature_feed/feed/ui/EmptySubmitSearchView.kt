package takeaway.feature_feed.feed.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SearchView
import takeaway.feature_feed.R

class EmptySubmitSearchView : SearchView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    lateinit var searchSrcTextView: SearchAutoComplete
    lateinit var listener: OnQueryTextListener

    override fun setOnQueryTextListener(listener: OnQueryTextListener) {
        super.setOnQueryTextListener(listener)
        this.listener = listener
        searchSrcTextView = this.findViewById(R.id.search_src_text)

        searchSrcTextView.setOnEditorActionListener { _, _, _ ->
            listener.onQueryTextSubmit(query.toString())
        }
    }
}