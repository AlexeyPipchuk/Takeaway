package takeaway.shared_category.data.datasource

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.component_test.TestSchedulerRule
import takeaway.shared_category.data.api.CategoryApi
import takeaway.shared_category.data.model.CategoryListResponse
import takeaway.shared_category.domain.entity.Category

@RunWith(MockitoJUnitRunner::class)
class CategoryDataSourceTest {

    @Rule
    @JvmField
    val testRule = TestSchedulerRule()

    private val categoryApi: CategoryApi = mock()
    private val dataSource = CategoryDataSource(categoryApi)

    @Test
    fun `get from cache EXPECT null`() {
        assertEquals(dataSource.getListFromCache(), null)
    }

    @Test
    fun `get list from cache after set cache EXPECT get list from cache`() {
        val categories = listOf(Category(id = 1, title = "category"))

        dataSource.setCache(categories)

        assertEquals(dataSource.getListFromCache(), categories)
    }

    @Test
    fun `get list from network EXPECT get list from network`() {
        val categories = listOf(Category(id = 1, title = "category"))
        val categoriesResponse = CategoryListResponse(categories)
        whenever(categoryApi.getCategoryList()).thenReturn(Single.just(categoriesResponse))

        dataSource.getListFromNetwork()
            .test()
            .assertValue(categoriesResponse)
    }
}