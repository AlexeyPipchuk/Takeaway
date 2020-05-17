package takeaway.feature.order.registration.domain.usecase.validation

import javax.inject.Inject

class ValidateSingleNumberUseCase @Inject constructor() {

    operator fun invoke(singleNumber: String, required: Boolean): String? = null
}