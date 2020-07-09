package takeaway.feature.cafe.ui

import takeaway.app.TakeawayView
import takeaway.feature.cafe.presentation.model.CategoryItem
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

    fun setProducts(productList: List<domain.entity.Product>)

    fun setCategories(categoryList: List<CategoryItem>)

    fun updateCategories(categoryList: List<CategoryItem>)

    fun updateProducts(productList: List<domain.entity.Product>)

    fun showProgress()

    fun hideProgress()

    fun showNoInternetDialog()

    fun showServiceUnavailable()

    fun showProductDialog(product: domain.entity.Product, cafe: Cafe)

    fun setBasketAmount(basketAmount: Int)
}