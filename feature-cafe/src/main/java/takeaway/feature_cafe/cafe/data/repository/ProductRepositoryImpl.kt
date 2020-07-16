package takeaway.feature_cafe.cafe.data.repository

import domain.entity.Product
import io.reactivex.Single
import takeaway.feature_cafe.cafe.data.datasource.ProductDataSource
import takeaway.feature_cafe.cafe.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource
) : ProductRepository {

    override fun getList(cafeId: String): Single<List<Product>> =
        dataSource.getList(cafeId)
            .map { it.productList }
}