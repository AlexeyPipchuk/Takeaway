package takeaway.feature.cafe.presentation

import takeaway.app.BasePresenter
import takeaway.feature.cafe.domain.entity.Product
import takeaway.feature.cafe.ui.product.ProductView
import javax.inject.Inject

class ProductPresenter @Inject constructor(
    private val product: Product
) : BasePresenter<ProductView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.showProductInfo(product)
    }

    fun onPlusButtonClicked() {
        view?.incPrice(product.price)
    }

    fun onMinusButtonClicked() {
        view?.decPrice(product.price)
    }

    fun onExitButtonClicked() {
        view?.closeDialog()
    }

    fun onToBasketButtonClicked(count: Int) {

        view?.closeDialog()
    }
}