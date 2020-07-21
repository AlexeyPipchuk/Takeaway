package takeaway.component_validation.domain.usecase

import org.junit.Assert.*
import org.junit.Test

class ValidateNameUseCaseTest {

    private companion object {
        const val MIN_NAME_LENGTH = 2
        const val MAX_NAME_LENGTH = 25
        const val SMALL_NAME_ERROR_TEXT = "Слишком короткое имя"
        const val BIG_NAME_ERROR_TEXT = "Слишком длинное имя"
        const val EMPTY_NAME = "Это обязательное поле"
        const val WRONG_NAME = "Некорректно набрано имя"
    }

    private val useCase = ValidateNameUseCase()

    @Test
    fun `name is empty EXPECT empty name result`() {
        val name = ""

        val result = useCase(name)

        assertEquals(EMPTY_NAME, result)
    }

    @Test
    fun `name less than min name length EXPECT small name result`() {
        val name = "a".repeat(MIN_NAME_LENGTH - 1)

        val result = useCase(name)

        assertEquals(SMALL_NAME_ERROR_TEXT, result)
    }

    @Test
    fun `name is more than max name length EXPECT big name result`() {
        val name = "a".repeat(MAX_NAME_LENGTH + 1)

        val result = useCase(name)

        assertEquals(BIG_NAME_ERROR_TEXT, result)
    }

    @Test
    fun `name does not fit on name regex EXPECT wrong name result`() {
        val name = "123"

        val result = useCase(name)

        assertEquals(WRONG_NAME, result)
    }

    @Test
    fun `name is correct EXPECT null`() {
        val name = "John"

        val result = useCase(name)

        assertEquals(null, result)
    }
}