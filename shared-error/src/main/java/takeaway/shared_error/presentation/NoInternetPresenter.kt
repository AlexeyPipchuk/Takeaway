package takeaway.shared_error.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import base.BasePresenter
import takeaway.shared_cafe.domain.usecase.GetCafeListUseCase
import javax.inject.Inject

class NoInternetPresenter @Inject constructor(
    private val router: NoInternetRouter,
    private val getCafeListUseCase: GetCafeListUseCase
) : BasePresenter<NoInternetView>() {

    fun onRetryConnectClicked() {
        view?.showProgress()

        getCafeListUseCase(useCache = false)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    router.toFeedScreen()
                },
                {
                    view?.hideProgress()
                }
            )
            .addToDisposable()
    }

    fun onTakeawayInfoClicked() {
        router.toInfoScreen()
    }

    fun onBackClicked() {
        view?.closeApp()
    }
}