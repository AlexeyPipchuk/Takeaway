package takeaway.shared_phone_prefix.domain.repository

interface PhoneCountryRepository {

    fun getPhoneCountryPrefix(): String
}