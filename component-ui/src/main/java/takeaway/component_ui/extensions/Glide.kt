package takeaway.component_ui.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

const val serverBaseUrl = "https://oleksey-oleksandrovich.ru:1337/"

fun ImageView.loadImage(url: String) {
    Glide
        .with(this)
        .load(serverBaseUrl + url)
        .into(this)
}