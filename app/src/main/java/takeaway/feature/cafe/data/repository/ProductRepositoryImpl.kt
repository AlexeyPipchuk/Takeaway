package takeaway.feature.cafe.data.repository

import io.reactivex.Single
import takeaway.feature.cafe.data.datasource.ProductDataSource
import takeaway.feature.cafe.domain.entity.Product
import takeaway.feature.cafe.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource
) : ProductRepository {

    override fun getList(cafeId: String): Single<List<Product>> =
        dataSource.getList(cafeId)
            .map { it.productList }
}