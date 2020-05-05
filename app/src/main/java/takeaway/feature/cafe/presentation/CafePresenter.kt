package takeaway.feature.cafe.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.navigation.Screen
import takeaway.shared.cafe.domain.entity.Product
import takeaway.shared.cafe.domain.usecase.GetProductListUseCase
import takeaway.feature.cafe.ui.CafeView
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.shared.basket.domian.usecase.GetBasketAmountUseCase
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class CafePresenter @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val cafe: Cafe,
    private val router: Router,
    private val getBasketAmountUseCase: GetBasketAmountUseCase
) : BasePresenter<CafeView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        loadProductList()
    }

    private fun loadProductList() {
        view?.showProgress()

        getProductListUseCase(cafe.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { products ->
                    view?.showCafeInfo(cafe)
                    view?.setProducts(products)
                    view?.hideProgress()
                },
                { error ->
                    view?.hideProgress()
                    handleError(error)
                }
            )
            .addToDisposable()
    }

    fun onRetryClicked() {
        loadProductList()
    }

    fun onBackClicked() {
        router.backTo(null)
    }

    fun onProductClicked(selectedProduct: Product) {
        view?.showProductDialog(selectedProduct, cafe)
    }

    fun onBasketClick() {
        router.navigateTo(Screen.BasketScreen)
    }

    private fun handleError(error: Throwable) {
        //TODO(Сделать ErrorConverter)
        if (error is UnknownHostException || error is SocketTimeoutException) {
            view?.showNoInternetDialog()
        } else {
            view?.showServiceUnavailable()
        }
    }

    fun onScreenUpdated() {
        val actualBasketAmount = getBasketAmountUseCase()
        view?.setBasketAmount(actualBasketAmount)
    }
}