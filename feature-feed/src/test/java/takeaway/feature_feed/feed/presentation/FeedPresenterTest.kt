package takeaway.feature_feed.feed.presentation

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.component_test.TestSchedulerRule
import takeaway.feature_feed.feed.presentation.model.Promo
import takeaway.feature_feed.feed.presentation.model.Separator
import takeaway.shared_cafe.domain.usecase.GetCafeListUseCase
import takeaway.shared_error.ErrorConverter
import takeaway.shared_error.ErrorType

@RunWith(MockitoJUnitRunner::class)
class FeedPresenterTest {

    @Rule
    @JvmField
    val testRule = TestSchedulerRule()

    private val view: FeedView = mock()

    private val getCafeListUseCase: GetCafeListUseCase = mock()
    private val errorConverter: ErrorConverter = mock()
    private val router: FeedRouter = mock()
    private val noInternet = false

    private val presenter = FeedPresenter(
        getCafeListUseCase,
        errorConverter,
        router,
        noInternet
    )
//
////    @Test
////    fun `view attached and no internet is true EXPECT route to error screen`() {
////        val presenter = createPresenter(noInternet = true)
////        //presenter.attachView(view)
////
////        //verify(router).toErrorScreen()
////    }

    @Test
    fun `view attached and no internet is false EXPECT do not route to error screen`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)

        presenter.attachView(view)

        verify(router, never()).toErrorScreen()
    }

    @Test
    fun `view attached and no internet is false EXPECT show progress`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)

        presenter.attachView(view)

        verify(view).showProgress()
    }

    @Test
    fun `view attached and no internet is false EXPECT load cafe list from cache`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)

        presenter.attachView(view)

        verify(getCafeListUseCase).invoke(useCache = true)
    }

    @Test
    fun `cafe list loaded EXPECT set feed`() {
        val popularCafe = cafe.copy(isPopular = true)
        val notPopularCafe = cafe.copy(isPopular = false)
        val popularCafeItem = cafeItem.copy(isPopular = true)
        val notPopularCafeItem = cafeItem.copy(isPopular = false)
        val cafeList = listOf(popularCafe, notPopularCafe)
        val feedItemsList = listOf(
            Separator.POPULAR,
            Promo.DEFAULT,
            popularCafeItem,
            Separator.NOT_POPULAR,
            notPopularCafeItem
        )
        whenever(getCafeListUseCase(any())).thenReturn(Single.just(cafeList))
        val presenter = createPresenter(noInternet = false)

        presenter.attachView(view)

        verify(view).setFeed(feedItemsList)
    }

    @Test
    fun `cafe list loaded EXPECT hide progress`() {
        val cafeList = listOf(cafe)
        whenever(getCafeListUseCase(any())).thenReturn(Single.just(cafeList))
        val presenter = createPresenter(noInternet = false)

        presenter.attachView(view)

        verify(view).hideProgress()
    }

    @Test
    fun `cafe list loading failed EXPECT hide progress`() {
        val error = Throwable("error")
        whenever(getCafeListUseCase(any())).thenReturn(Single.error(error))
        whenever(errorConverter.convert(error)).thenReturn(ErrorType.BAD_INTERNET)
        val presenter = createPresenter(noInternet = false)

        presenter.attachView(view)

        verify(view).hideProgress()
    }

    @Test
    fun `cafe list loading failed with bad internet EXPECT show no internet dialog`() {
        val error = Throwable("error")
        whenever(getCafeListUseCase(any())).thenReturn(Single.error(error))
        whenever(errorConverter.convert(error)).thenReturn(ErrorType.BAD_INTERNET)
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        verify(view).showNoInternetDialog()
    }

    @Test
    fun `cafe list loading failed with service unavailable EXPECT show service unavailable`() {
        val error = Throwable("error")
        whenever(getCafeListUseCase(any())).thenReturn(Single.error(error))
        whenever(errorConverter.convert(error)).thenReturn(ErrorType.SERVICE_UNAVAILABLE)
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        verify(view).showServiceUnavailable()
    }

    @Test
    fun `on fab menu button clicked EXPECT open fab menu`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onFabMenuButtonClicked()

        verify(view).openFabMenu()
    }

    @Test
    fun `on fab menu button clicked after same click EXPECT close fab menu`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onFabMenuButtonClicked()
        presenter.onFabMenuButtonClicked()

        verify(view).closeFabMenu()
    }

    @Test
    fun `on info button clicked EXPECT route to info screen`() {
        presenter.onInfoButtonClicked()

        verify(router).toInfoScreen()
    }

    @Test
    fun `on add cafe button clicked EXPECT route to add new cafe screen`() {
        presenter.onAddCafeButtonClicked()

        verify(router).toAddNewCafeScreen()
    }

    @Test
    fun `on refresh EXPECT hide empty search result`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onRefresh()

        verify(view).hideEmptySearchResult()
    }

    @Test
    fun `on refresh EXPECT clear search query`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onRefresh()

        verify(view).clearSearchQuery()
    }

    @Test
    fun `on refresh EXPECT load cafe from network`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onRefresh()

        verify(getCafeListUseCase).invoke(useCache = false)
    }

    @Test
    fun `on search query submit EXPECT hide empty search result`() {
        val query = "query"
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onSearchQuerySubmit(query)

        verify(view).hideEmptySearchResult()
    }

    @Test
    fun `on search query submit and cache cafe list is empty EXPECT do not set feed`() {
        val query = "query"
        whenever(getCafeListUseCase(any())).thenReturn(Single.just(emptyList()))
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)
        clearInvocations(view)

        presenter.onSearchQuerySubmit(query)

        verify(view, never()).setFeed(any())
    }

    @Test
    fun `on search query submit and query is empty EXPECT set feed from cache`() {
        val query = ""
        val popularCafe = cafe.copy(isPopular = true)
        val notPopularCafe = cafe.copy(isPopular = false)
        val popularCafeItem = cafeItem.copy(isPopular = true)
        val notPopularCafeItem = cafeItem.copy(isPopular = false)
        val cafeList = listOf(popularCafe, notPopularCafe)
        val feedItemsList = listOf(
            Separator.POPULAR,
            Promo.DEFAULT,
            popularCafeItem,
            Separator.NOT_POPULAR,
            notPopularCafeItem
        )
        whenever(getCafeListUseCase(any())).thenReturn(Single.just(cafeList))
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onSearchQuerySubmit(query)

        verify(view).setFeed(feedItemsList)
    }

    @Test
    fun `on search query submit and filtered list on query is empty EXPECT set feed from empty list`() {
        val query = "3"
        val popularCafe = cafe.copy(name = "cafe1", isPopular = true)
        val notPopularCafe = cafe.copy(name = "cafe2", isPopular = false)
        val cafeList = listOf(popularCafe, notPopularCafe)
        whenever(getCafeListUseCase(any())).thenReturn(Single.just(cafeList))
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onSearchQuerySubmit(query)

        verify(view).setFeed(emptyList())
    }

    @Test
    fun `on search query submit and filtered list on query is empty EXPECT show empty search result`() {
        val query = "3"
        val popularCafe = cafe.copy(name = "cafe1", isPopular = true)
        val notPopularCafe = cafe.copy(name = "cafe2", isPopular = false)
        val cafeList = listOf(popularCafe, notPopularCafe)
        whenever(getCafeListUseCase(any())).thenReturn(Single.just(cafeList))
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onSearchQuerySubmit(query)

        verify(view).showEmptySearchResult()
    }

    @Test
    fun `on search query submit and cafe list have some cafe like in query EXPECT set filtered feed`() {
        val query = "cafe"
        val popularCafe = cafe.copy(isPopular = true)
        val notPopularCafe = cafe.copy(isPopular = false)
        val popularCafeItem = cafeItem.copy(isPopular = true)
        val notPopularCafeItem = cafeItem.copy(isPopular = false)
        val cafeList = listOf(popularCafe, notPopularCafe)
        val feedItemsList = listOf(
            Separator.POPULAR,
            Promo.DEFAULT,
            popularCafeItem,
            Separator.NOT_POPULAR,
            notPopularCafeItem
        )
        whenever(getCafeListUseCase(any())).thenReturn(Single.just(cafeList))
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onSearchQuerySubmit(query)

        verify(view).setFeed(feedItemsList)
    }

    @Test
    fun `on clear clicked EXPECT hide empty search result`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onClearClicked()

        verify(view).hideEmptySearchResult()
    }

    @Test
    fun `on clear clicked EXPECT clear search query`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onClearClicked()

        verify(view).clearSearchQuery()
    }

    @Test
    fun `on clear clicked EXPECT set feed from cache`() {
        val popularCafe = cafe.copy(isPopular = true)
        val notPopularCafe = cafe.copy(isPopular = false)
        val popularCafeItem = cafeItem.copy(isPopular = true)
        val notPopularCafeItem = cafeItem.copy(isPopular = false)
        val cafeList = listOf(popularCafe, notPopularCafe)
        val feedItemsList = listOf(
            Separator.POPULAR,
            Promo.DEFAULT,
            popularCafeItem,
            Separator.NOT_POPULAR,
            notPopularCafeItem
        )
        whenever(getCafeListUseCase(any())).thenReturn(Single.just(cafeList))
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)
        clearInvocations(view)

        presenter.onClearClicked()

        verify(view).setFeed(feedItemsList)
    }

    @Test
    fun `on cafe clicked EXPECT route to cafe screen`() {
        val popularCafe = cafe.copy(isPopular = true)
        val popularCafeItem = cafeItem.copy(isPopular = true)
        val cafeList = listOf(popularCafe)
        whenever(getCafeListUseCase(any())).thenReturn(Single.just(cafeList))
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onCafeClicked(popularCafeItem)

        verify(router).toCafeScreen(popularCafe)
    }

    @Test
    fun `on promo clicked EXPECT show promo dialog`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        val presenter = createPresenter(noInternet = false)
        presenter.attachView(view)

        presenter.onPromoClicked()

        verify(view).showPromoDialog()
    }

    @Test
    fun `on retry clicked EXPECT load cafe list from network`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())

        presenter.onRetryClicked()

        verify(getCafeListUseCase).invoke(useCache = false)
    }

    @Test
    fun `on negative button clicked EXPECT route to error screen`() {
        presenter.onNegativeButtonClicked()

        verify(router).toErrorScreen()
    }

    private fun createPresenter(@Suppress("SameParameterValue") noInternet: Boolean): FeedPresenter =
        FeedPresenter(
            getCafeListUseCase = getCafeListUseCase,
            errorConverter = errorConverter,
            router = router,
            noInternet = noInternet
        )
}