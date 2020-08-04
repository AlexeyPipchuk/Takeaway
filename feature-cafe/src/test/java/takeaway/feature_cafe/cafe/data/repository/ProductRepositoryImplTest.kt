package takeaway.feature_cafe.cafe.data.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.feature_cafe.cafe.data.datasource.ProductDataSource
import takeaway.feature_cafe.cafe.data.model.ProductListResponse
import takeaway.feature_cafe.cafe.presentation.cafe
import takeaway.feature_cafe.cafe.presentation.product

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryImplTest {

    private val productDataSource: ProductDataSource = mock()
    private val repository = ProductRepositoryImpl(productDataSource)

    @Test
    fun `get list EXPECT product list`() {
        val cafeId = "123"
        val productList = listOf(product)
        val productResponse = ProductListResponse(productList)
        whenever(productDataSource.getList(cafeId)).thenReturn(Single.just(productResponse))

        repository.getList(cafeId)
            .test()
            .assertValue(productList)
    }
}