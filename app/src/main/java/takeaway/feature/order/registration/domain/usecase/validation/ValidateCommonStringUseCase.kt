package takeaway.feature.order.registration.domain.usecase.validation

import javax.inject.Inject

class ValidateCommonStringUseCase @Inject constructor() {

    companion object {
        const val REQUIRED_FIELD_IS_EMPTY = "Это обязательное поле"
        const val MAX_FIELD_LENGTH = 100
        const val WRONG_FIELD = "Поле заполнено некорректно"

        const val singleNumberRegex = ""
    }

    operator fun invoke(commonString: String, required: Boolean): String? =
        when {
            commonString.isEmpty() && required -> REQUIRED_FIELD_IS_EMPTY
            commonString.length > MAX_FIELD_LENGTH -> WRONG_FIELD
            // Regex(singleNumber).matches(singleNumberRegex) -> WRONG_FIELD
            else -> null
        }
}