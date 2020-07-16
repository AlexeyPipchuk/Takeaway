package takeaway.feature_cafe.product.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import takeaway.feature_cafe.R

class CustomCounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_counter_view, this, true)
    }
}