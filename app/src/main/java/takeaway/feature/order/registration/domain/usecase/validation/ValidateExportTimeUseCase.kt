package takeaway.feature.order.registration.domain.usecase.validation

import javax.inject.Inject

class ValidateExportTimeUseCase @Inject constructor() {

    companion object {
        const val EMPTY_AVAILABLE_EXPORT_TIME_LIST = "У заведения закончился рабочий день"
        const val EMPTY_TIME = "Это обязательное поле"
    }

    operator fun invoke(date: String, availableExportTimeList: List<String>): String? =
        when {
            availableExportTimeList.isEmpty() -> EMPTY_AVAILABLE_EXPORT_TIME_LIST
            date.isEmpty() -> EMPTY_TIME
            else -> null
        }
}