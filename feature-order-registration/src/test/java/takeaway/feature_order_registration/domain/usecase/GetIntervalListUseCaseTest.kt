package takeaway.feature_order_registration.domain.usecase

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetIntervalListUseCaseTest {

    private val useCase = GetIntervalListUseCase()

  //TODO(Привязка к календарю не дает нормально протестить)

//    @Test
//    fun `interval less than 15 minutes EXPECT empty list`() {
//        val startInterval = "14:00"
//        val endInterval = "14:10"
//
//        assertEquals(useCase(startInterval, endInterval), emptyList<String>())
//    }
//
//    @Test
//    fun `invoke EXPECT interval list`() {
//        val startInterval = "14:00"
//        val endInterval = "15:15"
//        val intervalList = listOf("14:15", "14:30", "14:45", "15:00")
//
//        assertEquals(useCase(startInterval, endInterval), intervalList)
//    }
}