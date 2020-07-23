package takeaway.shared_category.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_category.domain.entity.Category
import takeaway.shared_category.domain.repository.CategoryRepository

@RunWith(MockitoJUnitRunner::class)
class GetAllCategoryListUseCaseTest {

    private val categoryRepository: CategoryRepository = mock()
    private val useCase = GetAllCategoryListUseCase(categoryRepository)

    @Test
    fun `invoke EXPECT get all categories`() {
        val categories = listOf<Category>()
        whenever(categoryRepository.getList(useCache = false)).thenReturn(Single.just(categories))

        useCase()
            .test()
            .assertValue(categories)
    }
}