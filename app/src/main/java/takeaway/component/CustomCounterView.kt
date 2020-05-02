package takeaway.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.takeaway.R
import com.google.android.material.card.MaterialCardView

class CustomCounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_counter_view, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomCounterView, defStyleAttr, 0)
        try {
            applyTypedArray(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun applyTypedArray(typedArray: TypedArray) {
        //
    }
}