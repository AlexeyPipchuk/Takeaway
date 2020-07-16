package takeaway.feature_cafe.cafe.presentation

import base.TakeawayView
import domain.entity.Product
import takeaway.feature_cafe.cafe.presentation.model.CategoryItem
import takeaway.shared_cafe.domain.entity.Cafe

interface CafeView : TakeawayView {

    fun showMinDeliverySum(minDeliverySum: Int)

    fun showDeliveryPrice(deliveryPrice: Int)

    fun showDeliveryFreeFrom(freeFrom: Int)

    fun showBusinessLunch(businessFrom: String, businessTo: String)

    fun showTakeawayDiscount(takeawayDiscount: Int)

    fun showCafeInfo(
        cafeType: String,
        name: String,
        description: String,
        address: String,
        workFrom: String,
        workTo: String
    )

    fun showCafeImages(mainImg: List<String>?, logoImg: String?)

    fun setProducts(productList: List<Product>)

    fun setCategories(categoryList: List<CategoryItem>)

    fun updateCategories(categoryList: List<CategoryItem>)

    fun updateProducts(productList: List<Product>)

    fun showProgress()

    fun hideProgress()

    fun showNoInternetDialog()

    fun showServiceUnavailable()

    fun showProductDialog(product: Product, cafe: Cafe)

    fun setBasketAmount(basketAmount: Int)
}