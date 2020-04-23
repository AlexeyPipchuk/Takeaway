package takeaway.feature.cafe.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.feature.cafe.domain.usecase.GetProductListUseCase
import takeaway.feature.cafe.ui.CafeView
import takeaway.feature.feed.domain.entity.Cafe
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class CafePresenter @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val cafe: Cafe,
    private val router: Router
) : BasePresenter<CafeView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        loadProductList()
        view?.setCafeName(cafe.name)
    }

    private fun loadProductList() {

        getProductListUseCase(cafe.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { products ->

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

    private fun handleError(error: Throwable) {
        //TODO(Сделать ErrorConverter)
        if (error is UnknownHostException || error is SocketTimeoutException) {
            view?.showNoInternetDialog()
        } else {
            view?.showServiceUnavailable()
        }
    }
}