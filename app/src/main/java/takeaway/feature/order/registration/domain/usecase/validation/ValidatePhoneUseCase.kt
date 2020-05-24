package takeaway.feature.order.registration.domain.usecase.validation

import javax.inject.Inject

class ValidatePhoneUseCase @Inject constructor() {

    private companion object {
        const val MAX_PHONE_LENGTH = 13
        const val BIG_PHONE_ERROR_TEXT = "Слишком длинное имя"
        const val WRONG_PHONE = "Некорректный номер телефона"
        const val EMPTY_PHONE = "Это обязательное поле"

        const val russianPhoneRegex = "\n" +
                "^((\\+?7|8)[ \\-] ?)?((\\(\\d{3}\\))|(\\d{3}))?([ \\-])?(\\d{3}[\\- ]?\\d{2}[\\- ]?\\d{2})\$"
    }

    operator fun invoke(phone: String): String? =
        when {
            phone.isEmpty() -> EMPTY_PHONE
            phone.length > MAX_PHONE_LENGTH -> BIG_PHONE_ERROR_TEXT
            //   !Regex(russianPhoneRegex).matches(phone) -> WRONG_PHONE
            else -> null
        }
}