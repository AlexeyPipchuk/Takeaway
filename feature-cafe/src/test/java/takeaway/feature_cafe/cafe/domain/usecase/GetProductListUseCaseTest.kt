package takeaway.feature_cafe.cafe.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.feature_cafe.cafe.domain.repository.ProductRepository
import takeaway.feature_cafe.cafe.presentation.product

@RunWith(MockitoJUnitRunner::class)
class GetProductListUseCaseTest {

    private val productRepository: ProductRepository = mock()
    private val useCase = GetProductListUseCase(productRepository)

    @Test
    fun `invoke EXPECT product list with visible products`() {
        val cafeId = "123"
        val visibleProduct = product.copy(isVisible = true)
        val invisibleProduct = product.copy(isVisible = false)
        val productList = listOf(visibleProduct, invisibleProduct)
        whenever(productRepository.getList(cafeId)).thenReturn(Single.just(productList))

        useCase(cafeId)
            .test()
            .assertResult(listOf(visibleProduct))
    }
}