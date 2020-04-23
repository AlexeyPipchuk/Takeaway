package takeaway.feature.cafe.data.datasource

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import takeaway.feature.cafe.data.api.ProductApi
import takeaway.feature.cafe.data.model.ProductListResponse
import javax.inject.Inject

interface ProductDataSource {

    fun getList(cafeId: String): Single<ProductListResponse>
}

class ProductDataSourceImpl @Inject constructor(
    private val api: ProductApi
) : ProductDataSource {

    override fun getList(cafeId: String): Single<ProductListResponse> =
        api.getProductList(cafeId)
            .subscribeOn(Schedulers.io())
}