package takeaway.component_validation.domain.usecase

import org.junit.Assert.*
import org.junit.Test

class ValidateEmailUseCaseTest {

    private companion object {
        const val MAX_EMAIL_LENGTH = 40
        const val BIG_EMAIL_ERROR_TEXT = "Слишком длинный E-mail"
        const val WRONG_EMAIL = "Некорректный E-mail"
        const val EMPTY_EMAIL = "Это обязательное поле"
    }

    private val useCase = ValidateEmailUseCase()

    @Test
    fun `email is empty EXPECT empty email result`() {
        val email = ""

        val result = useCase(email)

        assertEquals(EMPTY_EMAIL, result)
    }

    @Test
    fun `email length more than max email length EXPECT big email error result`() {
        val email = "a".repeat(MAX_EMAIL_LENGTH + 1)

        val result = useCase(email)

        assertEquals(BIG_EMAIL_ERROR_TEXT, result)
    }

    @Test
    fun `email does not fit on email regex EXPECT wrong email result`() {
        val email = "test@"

        val result = useCase(email)

        assertEquals(WRONG_EMAIL, result)
    }

    @Test
    fun `email is correct EXPECT null`() {
        val email = "test@mail.com"

        val result = useCase(email)

        assertEquals(null, result)
    }
}