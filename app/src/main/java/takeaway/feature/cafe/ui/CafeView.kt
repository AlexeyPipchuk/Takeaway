package takeaway.feature.cafe.ui

import takeaway.app.TakeawayView
import takeaway.feature.cafe.presentation.model.CategoryItem
import takeaway.shared.cafe.domain.entity.Product
import takeaway.shared_cafe.domain.entity.Cafe

interface CafeView : TakeawayView {

    fun showCafeInfo(cafe: Cafe)

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