package takeaway.feature_cafe.cafe.data.datasource

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.component_test.TestSchedulerRule
import takeaway.feature_cafe.cafe.data.api.ProductApi
import takeaway.feature_cafe.cafe.data.model.ProductListResponse
import takeaway.feature_cafe.cafe.presentation.product

@RunWith(MockitoJUnitRunner::class)
class ProductDataSourceTest {

    @Rule
    @JvmField
    val testRule = TestSchedulerRule()

    private val productApi: ProductApi = mock()
    private val dataSource = ProductDataSource(productApi)

    @Test
    fun `get list EXPECT product list response`() {
        val cafeId = "123"
        val productList = listOf(product)
        val productResponse = ProductListResponse(productList)
        whenever(productApi.getProductList(cafeId)).thenReturn(Single.just(productResponse))

        dataSource.getList(cafeId)
            .test()
            .assertValue(productResponse)
    }
}