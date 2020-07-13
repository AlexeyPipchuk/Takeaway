package takeaway.feature_add_cafe.domain.entity

data class NewCafeRequest(
    val name: String,
    val email: String,
    val phone: String
)