package takeaway.feature_cafe.cafe.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.feature_cafe.cafe.presentation.category
import takeaway.shared_category.domain.repository.CategoryRepository

@RunWith(MockitoJUnitRunner::class)
class GetCategoryListUseCaseTest {

    private val categoryRepository: CategoryRepository = mock()
    private val useCase = GetCategoryListUseCase(categoryRepository)

    @Test
    fun `invoke with cafe categories is null EXPECT empty list`() {
        whenever(categoryRepository.getList(useCache = true)).thenReturn(Single.just(listOf(category)))

        useCase(useCache = true, cafeCategories = null)
            .test()
            .assertResult(emptyList())
    }

    @Test
    fun `invoke with cafe categories is not null EXPECT cafe categories for id`() {
        val categoryId = 1
        val secondCategoryId = 2
        val category = category.copy(id = categoryId)
        val secondCategory = category.copy(id = secondCategoryId)
        val categoriesList = listOf(category, secondCategory)

        whenever(categoryRepository.getList(useCache = true)).thenReturn(Single.just(categoriesList))

        useCase(useCache = true, cafeCategories = listOf(secondCategoryId))
            .test()
            .assertResult(listOf(secondCategory))
    }
}