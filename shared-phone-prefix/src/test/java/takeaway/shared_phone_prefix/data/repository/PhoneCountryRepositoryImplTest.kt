package takeaway.shared_phone_prefix.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_phone_prefix.data.datasource.PhoneCountryDataSource

@RunWith(MockitoJUnitRunner::class)
class PhoneCountryRepositoryImplTest {

    private val phoneCountryDataSource: PhoneCountryDataSource = mock()
    private val repository = PhoneCountryRepositoryImpl(phoneCountryDataSource)

    @Test
    fun `get phone country prefix EXPECT phone country prefix`() {
        val phonePrefix = "+7"
        whenever(phoneCountryDataSource.getPhoneCountryPrefix()).thenReturn(phonePrefix)

        assertEquals(repository.getPhoneCountryPrefix(), phonePrefix)
    }
}