package takeaway.feature.order.registration.domain.usecase.validation

import javax.inject.Inject

class ValidateNameUseCase @Inject constructor() {

    private companion object {
        const val MIN_NAME_LENGTH = 2
        const val MAX_NAME_LENGTH = 25
        const val SMALL_NAME_ERROR_TEXT = "Слишком короткое имя"
        const val BIG_NAME_ERROR_TEXT = "Слишком длинное имя"
        const val EMPTY_NAME = "Это обязательное поле"
        const val WRONG_NAME = "Некорректно набрано имя"

        const val nameRegex = "^[a-zA-Zа-яА-Я]+$"
    }

    operator fun invoke(name: String): String? =
        when {
            name.isEmpty() -> EMPTY_NAME
            name.length < MIN_NAME_LENGTH -> SMALL_NAME_ERROR_TEXT
            name.length > MAX_NAME_LENGTH -> BIG_NAME_ERROR_TEXT
            !Regex(nameRegex).matches(name) -> WRONG_NAME
            else -> null
        }
}