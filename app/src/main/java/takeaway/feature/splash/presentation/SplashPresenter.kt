package takeaway.feature.splash.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.DeepLinkDefiner
import takeaway.app.navigation.DeepLinkValidator
import takeaway.app.navigation.Screen
import takeaway.app.zipWith
import takeaway.feature.splash.domain.entity.DeepLink
import takeaway.feature.splash.domain.usecase.GetDeepLinkUseCase
import takeaway.shared.category.domain.usecase.GetCategoryListUseCase
import takeaway.shared_cafe.domain.usecase.GetCafeListUseCase
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val getCafeListUseCase: GetCafeListUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getDeepLinkUseCase: GetDeepLinkUseCase,
    private val deepLinkDefiner: DeepLinkDefiner,
    private val deepLinkValidator: DeepLinkValidator,
    private val deepLink: String?,
    private val router: Router

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
                    chooseFirstScreen()
                },
                {
                    view?.showStatusBar()
                    router.newRootScreen(Screen.NoInternetScreen)
                }
            )
            .addToDisposable()
    }

    private fun chooseFirstScreen() {
        if (deepLink != null && deepLinkValidator(deepLink)) {
            openDeepLink(getDeepLinkUseCase(deepLink))
        } else router.newRootScreen(Screen.FeedScreen())
    }

    private fun openDeepLink(deepLink: DeepLink) {
        val screen = deepLinkDefiner(deepLink)

        screen?.let {
            router.newRootScreen(screen)
        } ?: router.newRootScreen(Screen.FeedScreen())
    }
}