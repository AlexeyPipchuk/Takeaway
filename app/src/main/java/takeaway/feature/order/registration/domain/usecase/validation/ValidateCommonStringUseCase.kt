package takeaway.feature.order.registration.domain.usecase.validation

import javax.inject.Inject

class ValidateCommonStringUseCase @Inject constructor() {

    operator fun invoke(commonString: String, required: Boolean): String? = null
}