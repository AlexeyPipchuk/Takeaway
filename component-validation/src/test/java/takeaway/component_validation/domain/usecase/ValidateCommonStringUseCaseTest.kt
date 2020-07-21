package takeaway.component_validation.domain.usecase

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ValidateCommonStringUseCaseTest {

    private companion object {
        const val REQUIRED_FIELD_IS_EMPTY = "Это обязательное поле"
        const val MAX_FIELD_LENGTH = 100
        const val WRONG_FIELD = "Поле заполнено некорректно"
    }

    private val useCase = ValidateCommonStringUseCase()

    @Test
    fun `input string is empty and is required EXPECT required field is empty result`() {
        val inputString = ""

        val result = useCase(inputString, required = true)

        assertEquals(REQUIRED_FIELD_IS_EMPTY, result)
    }

    @Test
    fun `input string more than max field length EXPECT wrong field result`() {
        val inputString = "a".repeat(MAX_FIELD_LENGTH + 1)

        val result = useCase(inputString, required = true)

        assertEquals(WRONG_FIELD, result)
    }

    @Test
    fun `input string is correct EXPECT null`() {
        val inputString = "correct"

        val result = useCase(inputString, required = true)

        assertEquals(null, result)
    }
}