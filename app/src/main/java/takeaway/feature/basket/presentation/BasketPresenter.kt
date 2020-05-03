package takeaway.feature.basket.presentation

import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.feature.basket.ui.BasketView
import takeaway.shared.basket.domian.entity.Basket
import takeaway.shared.basket.domian.usecase.GetBasketUseCase
import javax.inject.Inject

class BasketPresenter @Inject constructor(
    private val router: Router,
    private val getBasketUseCase: GetBasketUseCase
) : BasePresenter<BasketView>() {

    private var basket: Basket = getBasketUseCase()

    override fun onViewAttach() {
        super.onViewAttach()

        updateCacheBasket()
    }

    private fun updateCacheBasket() {
        basket = getBasketUseCase()

        if (basket.isBasketNotEmpty()) {
            basket.cafe?.let { view?.showOrderCafeInfo(it) }
        } else {
            view?.showEmptyContent()
        }
    }

    private fun Basket.isBasketNotEmpty() =
        cafe != null && !products.isNullOrEmpty()

    fun onBackButtonClick() {
        router.exit()
    }
}