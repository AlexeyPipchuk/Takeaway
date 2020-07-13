package takeaway.component_validation.domain.usecase

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    private companion object {
        const val MAX_EMAIL_LENGTH = 40
        const val BIG_EMAIL_ERROR_TEXT = "Слишком длинный E-mail"
        const val WRONG_EMAIL = "Некорректный E-mail"
        const val EMPTY_EMAIL = "Это обязательное поле"

        const val emailRegex =
            "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    }

    operator fun invoke(email: String): String? =
        when {
            email.isEmpty() -> EMPTY_EMAIL
            email.length > MAX_EMAIL_LENGTH -> BIG_EMAIL_ERROR_TEXT
            !Regex(emailRegex).matches(email) -> WRONG_EMAIL
            else -> null
        }
}