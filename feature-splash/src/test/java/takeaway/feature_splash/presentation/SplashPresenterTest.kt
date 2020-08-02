package takeaway.feature_splash.presentation

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.component_test.TestSchedulerRule
import takeaway.feature_splash.cafe
import takeaway.feature_splash.category
import takeaway.feature_splash.domain.DeepLinkValidator
import takeaway.feature_splash.domain.entity.DeepLink
import takeaway.feature_splash.domain.usecase.GetDeepLinkUseCase
import takeaway.shared_cafe.domain.usecase.GetCafeListUseCase
import takeaway.shared_category.domain.usecase.GetAllCategoryListUseCase

@RunWith(MockitoJUnitRunner::class)
class SplashPresenterTest {

    @Rule
    @JvmField
    val testRule = TestSchedulerRule()

    private val getCafeListUseCase: GetCafeListUseCase = mock()
    private val getAllCategoryListUseCase: GetAllCategoryListUseCase = mock()
    private val getDeepLinkUseCase: GetDeepLinkUseCase = mock()
    private val deepLinkValidator: DeepLinkValidator = mock()
    private val deepLink = "https://deep.takeaway.com/success?orderId=4"
    private val router: SplashRouter = mock()

    private val view: SplashView = mock()

    private val presenter = SplashPresenter(
        getCafeListUseCase,
        getAllCategoryListUseCase,
        getDeepLinkUseCase,
        deepLinkValidator,
        deepLink,
        router
    )

    @Test
    fun `view attached EXPECT hide status bar`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        whenever(getAllCategoryListUseCase(any())).thenReturn(Single.never())

        presenter.attachView(view)

        verify(view).hideStatusBar()
    }

    @Test
    fun `view attached EXPECT cache cafe and categories`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        whenever(getAllCategoryListUseCase(any())).thenReturn(Single.never())

        presenter.attachView(view)

        verify(getCafeListUseCase).invoke(false)
        verify(getAllCategoryListUseCase).invoke(false)
    }

    @Test
    fun `cafe and categories loading failed EXPECT hide status bar`() {
        whenever(getCafeListUseCase(false)).thenReturn(Single.error(Throwable()))
        whenever(getAllCategoryListUseCase(false)).thenReturn(Single.error(Throwable()))

        presenter.attachView(view)

        verify(view).showStatusBar()
    }

    @Test
    fun `cafe and categories loading failed EXPECT route to no internet`() {
        whenever(getCafeListUseCase(false)).thenReturn(Single.error(Throwable()))
        whenever(getAllCategoryListUseCase(false)).thenReturn(Single.error(Throwable()))

        presenter.attachView(view)

        verify(router).toNoInternet()
    }

    @Test
    fun `cafe and categories loaded EXPECT show status bar`() {
        whenever(getCafeListUseCase(false)).thenReturn(Single.just(listOf(cafe)))
        whenever(getAllCategoryListUseCase(false)).thenReturn(Single.just(listOf(category)))

        presenter.attachView(view)

        verify(view).showStatusBar()
    }

    @Test
    fun `cafe and categories loaded and deep link is valid EXPECT route to deep link`() {
        val deepLinkEntity = DeepLink("/success", mapOf("orderId" to "4"))
        whenever(getCafeListUseCase(false)).thenReturn(Single.just(listOf(cafe)))
        whenever(getAllCategoryListUseCase(false)).thenReturn(Single.just(listOf(category)))
        whenever(deepLinkValidator(deepLink)).thenReturn(true)
        whenever(getDeepLinkUseCase(deepLink)).thenReturn(deepLinkEntity)

        presenter.attachView(view)

        verify(router).toScreenOnDeepLink(deepLinkEntity)
    }

    @Test
    fun `cafe and categories loaded and deep link is not valid EXPECT route to feed`() {
        whenever(getCafeListUseCase(false)).thenReturn(Single.just(listOf(cafe)))
        whenever(getAllCategoryListUseCase(false)).thenReturn(Single.just(listOf(category)))
        whenever(deepLinkValidator(deepLink)).thenReturn(false)

        presenter.attachView(view)

        verify(router).toFeed()
    }
}