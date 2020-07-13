package takeaway.component_validation.domain.usecase

import javax.inject.Inject

class ValidateSingleNumberUseCase @Inject constructor() {

    companion object {
        const val REQUIRED_FIELD_IS_EMPTY = "Это обязательное поле"
        const val MAX_FIELD_LENGTH = 5
        const val WRONG_FIELD = "Поле заполнено некорректно"
    }

    operator fun invoke(singleNumber: String, required: Boolean): String? =
        when {
            singleNumber.isEmpty() && required -> REQUIRED_FIELD_IS_EMPTY
            singleNumber.length > MAX_FIELD_LENGTH -> WRONG_FIELD
            else -> null
        }
}