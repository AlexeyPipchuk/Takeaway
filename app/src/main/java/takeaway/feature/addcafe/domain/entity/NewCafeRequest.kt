package takeaway.feature.addcafe.domain.entity

data class NewCafeRequest(
    val name: String,
    val email: String,
    val phone: String
)