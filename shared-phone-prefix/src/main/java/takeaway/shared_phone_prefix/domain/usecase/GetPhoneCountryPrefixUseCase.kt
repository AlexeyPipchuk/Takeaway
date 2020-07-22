package takeaway.shared_phone_prefix.domain.usecase

import takeaway.shared_phone_prefix.domain.repository.PhoneCountryRepository
import javax.inject.Inject

class GetPhoneCountryPrefixUseCase @Inject constructor(
    private val repository: PhoneCountryRepository
) {

    operator fun invoke(): String =
        repository.getPhoneCountryPrefix()
}