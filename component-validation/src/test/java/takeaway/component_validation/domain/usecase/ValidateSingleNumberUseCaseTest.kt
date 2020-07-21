package takeaway.component_validation.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidateSingleNumberUseCaseTest {

    private companion object {
        const val REQUIRED_FIELD_IS_EMPTY = "Это обязательное поле"
        const val MAX_FIELD_LENGTH = 5
        const val WRONG_FIELD = "Поле заполнено некорректно"
    }

    private val useCase = ValidateSingleNumberUseCase()

    @Test
    fun `number is empty and it is required EXPECT required field is empty result`() {
        val number = ""

        val result = useCase(number, required = true)

        assertEquals(REQUIRED_FIELD_IS_EMPTY, result)
    }

    @Test
    fun `number is more than max single number length EXPECT wrong field result`() {
        val number = "1".repeat(MAX_FIELD_LENGTH + 1)

        val result = useCase(number, required = false)

        assertEquals(WRONG_FIELD, result)
    }

    @Test
    fun `number is correct EXPECT null`() {
        val number = "543"

        val result = useCase(number, required = false)

        assertEquals(null, result)
    }
}