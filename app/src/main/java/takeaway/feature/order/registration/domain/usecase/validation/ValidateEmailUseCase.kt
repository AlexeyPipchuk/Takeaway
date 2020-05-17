package takeaway.feature.order.registration.domain.usecase.validation

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    private companion object {
        const val MAX_EMAIL_LENGTH = 5
        const val BIG_EMAIL_ERROR_TEXT = "Слишком длинное имя"
    }

    operator fun invoke(email: String): String? =
        if (email.length > MAX_EMAIL_LENGTH) BIG_EMAIL_ERROR_TEXT
        else null
}