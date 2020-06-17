package domain.usecase

import domain.repository.PhoneCountryRepository
import javax.inject.Inject

class GetPhoneCountryPrefixUseCase @Inject constructor(
    private val repository: PhoneCountryRepository
) {

    operator fun invoke(): String =
        repository.getPhoneCountryPrefix()
}