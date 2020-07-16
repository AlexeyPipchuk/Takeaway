package takeaway.feature_splash.presentation

import base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import takeaway.component_rx_extension.zipWith
import takeaway.feature_splash.domain.DeepLinkValidator
import takeaway.feature_splash.domain.entity.DeepLink
import takeaway.feature_splash.domain.usecase.GetDeepLinkUseCase
import takeaway.shared_cafe.domain.usecase.GetCafeListUseCase
import takeaway.shared_category.domain.usecase.GetAllCategoryListUseCase
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val getCafeListUseCase: GetCafeListUseCase,
    private val getAllCategoryListUseCase: GetAllCategoryListUseCase,
    private val getDeepLinkUseCase: GetDeepLinkUseCase,
    private val deepLinkValidator: DeepLinkValidator,
    private val deepLink: String?,
    private val router: SplashRouter

) : BasePresenter<SplashView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.hideStatusBar()
        loadCafeList()
    }

    private fun loadCafeList() {
        //for caching
        getCafeListUseCase(useCache = false)
            .zipWith(getAllCategoryListUseCase(useCache = false))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.showStatusBar()
                    chooseFirstScreen()
                },
                {
                    view?.showStatusBar()
                    router.toNoInternet()
                }
            )
            .addToDisposable()
    }

    private fun chooseFirstScreen() {
        if (deepLink != null && deepLinkValidator(deepLink)) {
            openDeepLink(getDeepLinkUseCase(deepLink))
        } else router.toFeed()
    }

    private fun openDeepLink(deepLink: DeepLink) {
        router.toScreenOnDeepLink(deepLink)
    }
}