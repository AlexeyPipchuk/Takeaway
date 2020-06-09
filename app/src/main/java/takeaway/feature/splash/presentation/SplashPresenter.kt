package takeaway.feature.splash.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.navigation.Screen
import takeaway.app.zipWith
import takeaway.feature.feed.domain.usecase.GetCafeListUseCase
import takeaway.shared.category.domain.usecase.GetCategoryListUseCase
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val router: Router,
    private val getCafeListUseCase: GetCafeListUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase
) : BasePresenter<SplashView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.hideStatusBar()
        loadCafeList()
    }

    private fun loadCafeList() {
        //for caching
        getCafeListUseCase(useCache = false)
            .zipWith(getCategoryListUseCase(useCache = false))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.showStatusBar()
                    router.newRootScreen(Screen.FeedScreen())
                },
                {
                    view?.showStatusBar()
                    router.newRootScreen(Screen.NoInternetScreen)
                }
            )
            .addToDisposable()
    }
}