package takeaway.feature.cafe.presentation

import takeaway.app.BasePresenter
import takeaway.feature.cafe.domain.entity.Product
import takeaway.feature.cafe.ui.product.ProductView
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.shared.basket.domian.usecase.AddToBasketUseCase
import takeaway.shared.basket.domian.usecase.ClearBasketUseCase
import takeaway.shared.basket.domian.usecase.GetBasketCafeIdUseCase
import javax.inject.Inject

class ProductPresenter @Inject constructor(
    private val product: Product,
    private val cafe: Cafe,
    private val getBasketCafeIdUseCase: GetBasketCafeIdUseCase,
    private val clearBasketUseCase: ClearBasketUseCase,
    private val addToBasketUseCase: AddToBasketUseCase
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
        view?.closeProductDialog()
    }

    fun onToBasketButtonClicked(count: Int) {
        val cafeInBasketId = getBasketCafeIdUseCase()

        if (cafeInBasketId != null) {
            if (cafeInBasketId == cafe.id) {
                addToBasketUseCase(product, count, cafe)
                view?.closeProductDialog()
            } else {
                view?.showClearBasketQuestionDialog()
            }
        } else {
            addToBasketUseCase(product, count, cafe)
            view?.closeProductDialog()
        }
    }

    fun onApproveToClearBasketButtonClicked(count: Int) {
        clearBasketUseCase()
        
        addToBasketUseCase(product, count, cafe)
        view?.closeProductDialog()
    }
}