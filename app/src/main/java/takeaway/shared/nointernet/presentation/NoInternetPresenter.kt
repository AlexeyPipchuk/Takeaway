package takeaway.shared.nointernet.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.navigation.Screen
import takeaway.shared_cafe.domain.usecase.GetCafeListUseCase
import javax.inject.Inject

class NoInternetPresenter @Inject constructor(
    private val router: Router,
    private val getCafeListUseCase: GetCafeListUseCase
) : BasePresenter<NoInternetView>() {

    fun onRetryConnectClicked() {
        view?.showProgress()

        getCafeListUseCase(useCache = false)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    router.replaceScreen(Screen.FeedScreen())
                },
                {
                    view?.hideProgress()
                }
            )
            .addToDisposable()
    }

    fun onTakeawayInfoClicked() {
        router.navigateTo(Screen.InfoScreen)
    }

    fun onBackClicked() {
        view?.closeApp()
    }
}