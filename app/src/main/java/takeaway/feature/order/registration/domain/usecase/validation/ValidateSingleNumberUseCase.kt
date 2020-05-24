package takeaway.feature.order.registration.domain.usecase.validation

import javax.inject.Inject

class ValidateSingleNumberUseCase @Inject constructor() {

    companion object {
        const val REQUIRED_FIELD_IS_EMPTY = "Это обязательное поле"
        const val MAX_FIELD_LENGTH = 10
        const val WRONG_FIELD = "Поле заполнено некорректно"

        const val singleNumberRegex = ""
    }

    operator fun invoke(singleNumber: String, required: Boolean): String? =
        when {
            singleNumber.isEmpty() && required -> REQUIRED_FIELD_IS_EMPTY
            singleNumber.length > MAX_FIELD_LENGTH -> WRONG_FIELD
            // Regex(singleNumber).matches(singleNumberRegex) -> WRONG_FIELD
            else -> null
        }
}