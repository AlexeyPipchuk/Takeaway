package takeaway.shared_phone_prefix.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_phone_prefix.domain.repository.PhoneCountryRepository

@RunWith(MockitoJUnitRunner::class)
class GetPhoneCountryPrefixUseCaseTest {

    private val phoneCountryRepository: PhoneCountryRepository = mock()
    private val useCase = GetPhoneCountryPrefixUseCase(phoneCountryRepository)

    @Test
    fun `invoke EXPECT phone country prefix`() {
        val phonePrefix = "+7"
        whenever(phoneCountryRepository.getPhoneCountryPrefix()).thenReturn(phonePrefix)

        assertEquals(useCase(), phonePrefix)
    }
}