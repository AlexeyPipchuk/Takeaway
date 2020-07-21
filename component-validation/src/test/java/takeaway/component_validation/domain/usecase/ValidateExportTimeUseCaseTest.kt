package takeaway.component_validation.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidateExportTimeUseCaseTest {

    private companion object {
        const val EMPTY_AVAILABLE_EXPORT_TIME_LIST = "У заведения закончился рабочий день"
        const val EMPTY_TIME = "Это обязательное поле"
    }

    private val useCase = ValidateExportTimeUseCase()

    @Test
    fun `available export time list is empty EXPECT empty available export time list result`() {
        val availableExportTimeList = emptyList<String>()
        val date = "10:00"

        val result = useCase(date, availableExportTimeList)

        assertEquals(EMPTY_AVAILABLE_EXPORT_TIME_LIST, result)
    }

    @Test
    fun `date is empty EXPECT empty time result`() {
        val availableExportTimeList = listOf("10:00")
        val date = ""

        val result = useCase(date, availableExportTimeList)

        assertEquals(EMPTY_TIME, result)
    }

    @Test
    fun `correct invoke EXPECT null`() {
        val availableExportTimeList = listOf("11:00")
        val date = "10:00"

        val result = useCase(date, availableExportTimeList)

        assertEquals(null, result)
    }
}