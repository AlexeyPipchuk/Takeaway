package takeaway.feature_cafe.product.presentation

import base.BasePresenter
import domain.entity.Product
import takeaway.shared_basket.domain.usecase.AddToBasketUseCase
import takeaway.shared_basket.domain.usecase.ClearBasketUseCase
import takeaway.shared_basket.domain.usecase.GetBasketCafeIdUseCase
import takeaway.shared_cafe.domain.entity.Cafe
import javax.inject.Inject

class ProductPresenter @Inject constructor(
    private val getBasketCafeIdUseCase: GetBasketCafeIdUseCase,
    private val clearBasketUseCase: ClearBasketUseCase,
    private val addToBasketUseCase: AddToBasketUseCase,
    private val product: Product,
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

        when {
            cafeInBasketId != null && cafeInBasketId == cafe.id -> {
                addToBasketUseCase(product, count, cafe)
                view?.closeProductDialog()
            }

            cafeInBasketId != null -> view?.showClearBasketQuestionDialog()

            else -> {
                addToBasketUseCase(product, count, cafe)
                view?.closeProductDialog()
            }
        }
    }

    fun onApproveToClearBasketButtonClicked(count: Int) {
        clearBasketUseCase()

        addToBasketUseCase(product, count, cafe)
        view?.closeProductDialog()
    }
}