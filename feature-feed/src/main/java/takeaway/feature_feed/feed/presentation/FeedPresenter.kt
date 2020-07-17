package takeaway.feature_feed.feed.presentation

import android.os.Handler
import base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import takeaway.feature_feed.feed.presentation.model.CafeItem
import takeaway.feature_feed.feed.presentation.model.FeedItem
import takeaway.feature_feed.feed.presentation.model.Promo
import takeaway.feature_feed.feed.presentation.model.Separator
import takeaway.shared_cafe.domain.entity.Cafe
import takeaway.shared_cafe.domain.usecase.GetCafeListUseCase
import takeaway.shared_error.ErrorConverter
import takeaway.shared_error.ErrorType
import java.util.*
import javax.inject.Inject

class FeedPresenter @Inject constructor(
    private val getCafeListUseCase: GetCafeListUseCase,
    private val errorConverter: ErrorConverter,
    private val router: FeedRouter,
    private val noInternet: Boolean
) : BasePresenter<FeedView>() {

    private var cafeListCache: List<Cafe>? = null
    private var fabMenuOpened = false

    override fun onViewAttach() {
        super.onViewAttach()

        if (noInternet) {
            //TODO(Прочекать pending transaction)
            Handler().post {
                router.toErrorScreen()
            }
        } else {
            fabMenuOpened = false

            loadCafeList()
        }
    }

    private fun loadCafeList(useCache: Boolean = true) {
        view?.showProgress()

        getCafeListUseCase(useCache)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cafeList ->
                    cafeListCache = cafeList
                    showAllFeedFromCache()
                },
                { error ->
                    view?.hideProgress()
                    handleError(error)
                }
            )
            .addToDisposable()
    }

    private fun mapCafeToCafeItem(cafe: Cafe): CafeItem =
        CafeItem(
            cafeName = cafe.name,
            takeawayDiscount = cafe.takeawayDiscount,
            deliveryFreeFrom = cafe.deliveryFreeFrom,
            imageUrl = cafe.imgUrls?.firstOrNull(),
            logoUrl = cafe.logoUrl,
            isPopular = cafe.isPopular
        )

    private fun showAllFeedFromCache() {
        val cafeItemList = cafeListCache.orEmpty().map(::mapCafeToCafeItem)
        val feedList = createFeedList(cafeItemList)

        view?.setFeed(feedList)
        view?.hideProgress()
    }

    private fun createFeedList(cafeList: List<CafeItem>): List<FeedItem> =
        mutableListOf<FeedItem>(Separator.POPULAR)
            .apply {
                add(Promo())
                addAll(cafeList.filter { it.isPopular })
                add(Separator.NOT_POPULAR)
                addAll(cafeList.filter { !it.isPopular })
            }

    fun onFabMenuButtonClicked() {
        if (fabMenuOpened) {
            fabMenuOpened = false
            view?.closeFabMenu()
        } else {
            fabMenuOpened = true
            view?.openFabMenu()
        }
    }

    fun onInfoButtonClicked() {
        router.toInfoScreen()
    }

    fun onAddCafeButtonClicked() {
        router.toAddNewCafeScreen()
    }

    fun onRefresh() {
        view?.hideEmptySearchResult()
        view?.clearSearchQuery()
        loadCafeList(useCache = false)
    }

    fun onSearchQuerySubmit(query: String?) {
        view?.hideEmptySearchResult()

        when {
            cafeListCache.isNullOrEmpty() -> Unit

            query.isNullOrEmpty() ->
                cafeListCache?.let { view?.setFeed(it.map(::mapCafeToCafeItem)) }

            else -> showFeedListByQuery(query)
        }
    }

    private fun showFeedListByQuery(query: String) {
        val filteredList = cafeListCache?.filter {
            it.name.toUpperCase(Locale.ENGLISH).contains(
                query.toUpperCase(
                    Locale.ENGLISH
                ).trim()
            )
        }?.map(::mapCafeToCafeItem)

        if (filteredList.isNullOrEmpty()) {
            view?.setFeed(emptyList())
            view?.showEmptySearchResult()
        } else view?.setFeed(filteredList)
    }

    fun onClearClicked() {
        view?.hideEmptySearchResult()
        view?.clearSearchQuery()

        showAllFeedFromCache()
    }

    fun onCafeClicked(selectedCafe: CafeItem) {
        val cafe = cafeListCache?.firstOrNull { it.name == selectedCafe.cafeName }
        cafe?.let {
            router.toCafeScreen(it)
        }
    }

    fun onPromoClicked() {
        view?.showPromoDialog()
    }

    fun onRetryClicked() {
        loadCafeList(useCache = false)
    }

    fun onNegativeButtonClicked() {
        router.toErrorScreen()
    }

    private fun handleError(error: Throwable) {
        when (errorConverter.convert(error)) {
            ErrorType.BAD_INTERNET -> view?.showNoInternetDialog()
            ErrorType.SERVICE_UNAVAILABLE -> view?.showServiceUnavailable()
        }
    }
}