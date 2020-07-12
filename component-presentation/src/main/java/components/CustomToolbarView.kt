package components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.custom_toolbar_view.view.*
import takeaway.component_presentation.R

class CustomToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    init {
        initAttributes(context, attrs, defStyleAttr)
    }

    private fun initAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar_view, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomToolbarView, defStyleAttr, 0)
        try {
            toolbarBackIcon.setImageDrawable(typedArray.getDrawable(R.styleable.CustomToolbarView_backIcon))
            toolbarBackText.text = typedArray.getText(R.styleable.CustomToolbarView_backText)
            toolbarTitle.text = typedArray.getText(R.styleable.CustomToolbarView_toolbarTitle)
        } finally {
            typedArray.recycle()
        }
    }

    fun setBackButtonListener(action: () -> Unit = {}) {
        backButtonLayout.setOnClickListener { action() }
    }
}