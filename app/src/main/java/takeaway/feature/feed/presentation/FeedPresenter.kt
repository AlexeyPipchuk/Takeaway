package takeaway.feature.feed.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.navigation.Screen
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.feature.feed.domain.usecase.GetCafeListUseCase
import takeaway.feature.feed.presentation.model.CafeItem
import takeaway.feature.feed.presentation.model.FeedItem
import takeaway.feature.feed.presentation.model.Promo
import takeaway.feature.feed.presentation.model.Separator
import takeaway.feature.feed.ui.FeedView
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

class FeedPresenter @Inject constructor(
    private val getCafeListUseCase: GetCafeListUseCase,
    private val router: Router
) : BasePresenter<FeedView>() {

    private var cafeListCache: List<Cafe>? = null
    private var fabMenuOpened = false

    override fun onViewAttach() {
        super.onViewAttach()
        fabMenuOpened = false

        loadCafeList()
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
        router.navigateTo(Screen.InfoScreen)
    }

    fun onAddCafeButtonClicked() {

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
            router.navigateTo(Screen.CafeScreen(it))
        }
    }

    fun onPromoClicked() {
        view?.showPromoDialog()
    }

    fun onRetryClicked() {
        loadCafeList(useCache = false)
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