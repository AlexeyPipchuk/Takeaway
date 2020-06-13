package takeaway.app.navigation

import android.content.Context
import android.net.Uri
import com.example.takeaway.R
import takeaway.feature.splash.domain.entity.DeepLinks

class DeepLinkValidator(private val context: Context) {

    //correct sample https://deep.takeaway.com/success?orderId=4

    operator fun invoke(link: String): Boolean =
        isSchemeAndHostCorrect(Uri.parse(link))

    private fun isSchemeAndHostCorrect(uri: Uri): Boolean =
        isHostCorrect(uri.host) && isPathCorrect(uri.path)

    private fun isHostCorrect(host: String?): Boolean =
        host == context.getString(R.string.deep_link_host)

    private fun isPathCorrect(path: String?): Boolean =
        path?.let { itPath ->
            DeepLinks.values().any { it.path == itPath }
        } ?: false
}