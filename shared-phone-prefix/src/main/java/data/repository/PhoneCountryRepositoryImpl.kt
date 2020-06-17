package data.repository

import data.data.PhoneCountryDataSource
import domain.repository.PhoneCountryRepository
import javax.inject.Inject

class PhoneCountryRepositoryImpl @Inject constructor(
    private val dataSource: PhoneCountryDataSource
) : PhoneCountryRepository {

    override fun getPhoneCountryPrefix(): String =
        dataSource.getPhoneCountryPrefix()
}