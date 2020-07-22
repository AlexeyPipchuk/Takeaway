package takeaway.shared_phone_prefix.data.repository

import takeaway.shared_phone_prefix.data.datasource.PhoneCountryDataSource
import takeaway.shared_phone_prefix.domain.repository.PhoneCountryRepository
import javax.inject.Inject

class PhoneCountryRepositoryImpl @Inject constructor(
    private val dataSource: PhoneCountryDataSource
) : PhoneCountryRepository {

    override fun getPhoneCountryPrefix(): String =
        dataSource.getPhoneCountryPrefix()
}