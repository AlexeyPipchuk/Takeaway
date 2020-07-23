package takeaway.shared_cafe.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_cafe.cafe
import takeaway.shared_cafe.cafeListResponse
import takeaway.shared_cafe.data.datasource.CafeDataSource

@RunWith(MockitoJUnitRunner::class)
class CafeRepositoryImplTest {

    private val cafeDataSource: CafeDataSource = mock()
    private val repository = CafeRepositoryImpl(cafeDataSource)
    private val cafeList = listOf(cafe)

    @Test
    fun `get list with cache EXPECT cafe list from cache`() {
        whenever(cafeDataSource.getListFromCache()).thenReturn(cafeList)

        repository.getList(useCache = true)
            .test()
            .assertValue(cafeList)
    }

    @Test
    fun `get list with cache but cache is empty EXPECT cafe list from network`() {
        val unitedCafeList = cafeListResponse.popularCafeList.plus(cafeListResponse.cafeList)
        whenever(cafeDataSource.getListFromCache()).thenReturn(null)
        whenever(cafeDataSource.getListFromNetwork()).thenReturn(Single.just(cafeListResponse))

        repository.getList(useCache = true)
            .test()
            .assertValue(unitedCafeList)

        verify(cafeDataSource).getListFromCache()
        verify(cafeDataSource).getListFromNetwork()
        verify(cafeDataSource).setCache(unitedCafeList)
    }

    @Test
    fun `get list without cache EXPECT cafe list from network`() {
        val unitedCafeList = cafeListResponse.popularCafeList.plus(cafeListResponse.cafeList)
        whenever(cafeDataSource.getListFromNetwork()).thenReturn(Single.just(cafeListResponse))

        repository.getList(useCache = false)
            .test()
            .assertValue(unitedCafeList)

        verify(cafeDataSource).getListFromNetwork()
        verify(cafeDataSource).setCache(unitedCafeList)
    }
}