package takeaway.component_validation.domain.usecase

import org.junit.Assert.*
import org.junit.Test

class ValidatePhoneUseCaseTest {

    private companion object {
        const val MIN_PHONE_LENGTH = 18
        const val MAX_PHONE_LENGTH = 20
        const val WRONG_PHONE = "Некорректный номер телефона"
        const val EMPTY_PHONE = "Это обязательное поле"
    }

    private val useCase = ValidatePhoneUseCase()

    @Test
    fun `phone is empty EXPECT empty phone result`() {
        val phone = ""

        val result = useCase(phone)

        assertEquals(EMPTY_PHONE, result)
    }

    @Test
    fun `phone is less than min phone length EXPECT wrong phone result`() {
        val phone = "a".repeat(MIN_PHONE_LENGTH - 1)

        val result = useCase(phone)

        assertEquals(WRONG_PHONE, result)
    }

    @Test
    fun `phone is more than max phone length EXPECT wrong phone result`() {
        val phone = "a".repeat(MAX_PHONE_LENGTH + 1)

        val result = useCase(phone)

        assertEquals(WRONG_PHONE, result)
    }

    @Test
    fun `phone is correct EXPECT null`() {
        val phone = "+7 (999) 999 99-99"

        val result = useCase(phone)

        assertEquals(null, result)
    }
}