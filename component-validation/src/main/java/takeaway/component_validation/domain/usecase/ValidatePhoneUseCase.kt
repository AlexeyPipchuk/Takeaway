package takeaway.component_validation.domain.usecase

import javax.inject.Inject

class ValidatePhoneUseCase @Inject constructor() {

    private companion object {
        const val MIN_PHONE_LENGTH = 18
        const val MAX_PHONE_LENGTH = 20
        const val WRONG_PHONE = "Некорректный номер телефона"
        const val EMPTY_PHONE = "Это обязательное поле"
    }

    operator fun invoke(phone: String): String? =
        when {
            phone.isEmpty() -> EMPTY_PHONE
            phone.length < MIN_PHONE_LENGTH -> WRONG_PHONE
            phone.length > MAX_PHONE_LENGTH -> WRONG_PHONE
            else -> null
        }
}