package takeaway.feature.splash.domain.entity

import java.io.Serializable

data class DeepLink(
    val path: String,
    val params: Map<String, String> = emptyMap()
) : Serializable