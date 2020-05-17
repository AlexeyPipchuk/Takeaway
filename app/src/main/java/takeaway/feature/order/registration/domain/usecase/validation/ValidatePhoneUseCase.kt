package takeaway.feature.order.registration.domain.usecase.validation

import javax.inject.Inject

class ValidatePhoneUseCase @Inject constructor() {

    private companion object {
        const val MAX_PHONE_LENGTH = 5
        const val BIG_PHONE_ERROR_TEXT = "Слишком длинное имя"
    }

    operator fun invoke(phone: String): String? =
        if (phone.length > MAX_PHONE_LENGTH) BIG_PHONE_ERROR_TEXT
        else null
}