package takeaway.feature_cafe.cafe.data.datasource

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import takeaway.feature_cafe.cafe.data.api.ProductApi
import takeaway.feature_cafe.cafe.data.model.ProductListResponse
import javax.inject.Inject

class ProductDataSource @Inject constructor(
    private val api: ProductApi
) {

    fun getList(cafeId: String): Single<ProductListResponse> =
        api.getProductList(cafeId)
            .subscribeOn(Schedulers.io())
}