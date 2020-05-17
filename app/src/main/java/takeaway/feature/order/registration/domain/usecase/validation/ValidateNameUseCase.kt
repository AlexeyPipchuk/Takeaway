package takeaway.feature.order.registration.domain.usecase.validation

import javax.inject.Inject

class ValidateNameUseCase @Inject constructor() {

    private companion object {
        const val MAX_NAME_LENGTH = 5
        const val BIG_NAME_ERROR_TEXT = "Слишком длинное имя"
    }

    operator fun invoke(name: String): String? =
        if (name.length > MAX_NAME_LENGTH) BIG_NAME_ERROR_TEXT
        else null
}