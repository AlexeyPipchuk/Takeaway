package takeaway.feature.splash.domain.usecase

import android.net.Uri
import takeaway.feature.splash.domain.entity.DeepLink
import javax.inject.Inject

class GetDeepLinkUseCase @Inject constructor() {

    operator fun invoke(deepLink: String): DeepLink {
        val uri = Uri.parse(deepLink)

        return DeepLink(
            path = uri.path ?: "",
            params = uri.queryParameterNames
                .map { it to uri.getQueryParameter(it) as String }
                .toMap()
        )
    }
}