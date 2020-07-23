package takeaway.shared_category.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_category.data.datasource.CategoryDataSource
import takeaway.shared_category.data.model.CategoryListResponse
import takeaway.shared_category.domain.entity.Category

@RunWith(MockitoJUnitRunner::class)
class CategoryRepositoryImplTest {

    private val categoryDataSource: CategoryDataSource = mock()
    private val repository = CategoryRepositoryImpl(categoryDataSource)

    @Test
    fun `get list from cache EXPECT categories list from cache`() {
        val categories = Category(id = 1, title = "category")

        whenever(categoryDataSource.getListFromCache()).thenReturn(listOf(categories))

        repository.getList(useCache = true)
            .test()
            .assertValue(listOf(categories))
    }

    @Test
    fun `get list from empty cache EXPECT categories list from network`() {
        val categories = Category(id = 1, title = "category")
        val categoriesResponse = CategoryListResponse(listOf(categories))

        whenever(categoryDataSource.getListFromCache()).thenReturn(null)
        whenever(categoryDataSource.getListFromNetwork()).thenReturn(Single.just(categoriesResponse))

        repository.getList(useCache = true)
            .test()
            .assertValue(listOf(categories))
    }

    @Test
    fun `get list from network EXPECT categories list`() {
        val categories = Category(id = 1, title = "category")
        val categoriesResponse = CategoryListResponse(listOf(categories))

        whenever(categoryDataSource.getListFromNetwork()).thenReturn(Single.just(categoriesResponse))

        repository.getList(useCache = false)
            .test()
            .assertValue(listOf(categories))

        verify(categoryDataSource).getListFromNetwork()
    }
}