package takeaway.feature.order.registration.domain.usecase.validation

import javax.inject.Inject

class ValidateExportTimeUseCase @Inject constructor() {

    operator fun invoke(date: String, availableExportTimeList: List<String>): String? = null
}