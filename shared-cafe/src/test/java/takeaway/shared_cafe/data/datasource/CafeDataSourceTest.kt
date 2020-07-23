package takeaway.shared_cafe.data.datasource

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.component_test.TestSchedulerRule
import takeaway.shared_cafe.cafe
import takeaway.shared_cafe.cafeListResponse
import takeaway.shared_cafe.data.api.CafeApi

@RunWith(MockitoJUnitRunner::class)
class CafeDataSourceTest {

    @Rule
    @JvmField
    val testRule = TestSchedulerRule()

    private val cafeApi: CafeApi = mock()
    private val dataSource = CafeDataSource(cafeApi)

    @Test
    fun `get from cache EXPECT null`() {
        assertEquals(dataSource.getListFromCache(), null)
    }

    @Test
    fun `get list from cache EXPECT cafe list`() {
        dataSource.setCache(listOf(cafe))

        assertEquals(dataSource.getListFromCache(), listOf(cafe))
    }

    @Test
    fun `get cafe list after set cache EXPECT cafe list from cache`() {
        whenever(cafeApi.getCafeList()).thenReturn(Single.just(cafeListResponse))

        dataSource.getListFromNetwork()
            .test()
            .assertValue(cafeListResponse)
    }
}