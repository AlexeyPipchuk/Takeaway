package takeaway.feature.cafe.product.presentation

import base.BasePresenter
import takeaway.shared_basket.domain.usecase.AddToBasketUseCase
import takeaway.shared_basket.domain.usecase.ClearBasketUseCase
import takeaway.shared_basket.domain.usecase.GetBasketCafeIdUseCase
import takeaway.shared_cafe.domain.entity.Cafe
import javax.inject.Inject

class ProductPresenter @Inject constructor(
    private val getBasketCafeIdUseCase: takeaway.shared_basket.domain.usecase.GetBasketCafeIdUseCase,
    private val clearBasketUseCase: takeaway.shared_basket.domain.usecase.ClearBasketUseCase,
    private val addToBasketUseCase: takeaway.shared_basket.domain.usecase.AddToBasketUseCase,
    private val product: domain.entity.Product,
    private val cafe: Cafe
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