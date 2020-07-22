package takeaway.shared_error.presentation

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
import takeaway.shared_cafe.domain.usecase.GetCafeListUseCase

@RunWith(MockitoJUnitRunner::class)
class NoInternetPresenterTest {

    @Rule
    @JvmField
    val testRule = TestSchedulerRule()

    private val router: NoInternetRouter = mock()
    private val getCafeListUseCase: GetCafeListUseCase = mock()
    private val presenter = NoInternetPresenter(router, getCafeListUseCase)

    private val view: NoInternetView = mock()

    @Test
    fun `on retry connect clicked EXPECT show progress`() {
        whenever(getCafeListUseCase()).thenReturn(Single.never())
        presenter.attachView(view)

        presenter.onRetryConnectClicked()

        verify(view).showProgress()
    }

    @Test
    fun `on retry connect clicked EXPECT get cafe list without cache`() {
        whenever(getCafeListUseCase(any())).thenReturn(Single.never())
        presenter.attachView(view)

        presenter.onRetryConnectClicked()

        verify(getCafeListUseCase).invoke(useCache = false)
    }

    @Test
    fun `cafe list loaded EXPECT route to feed`() {
        whenever(getCafeListUseCase()).thenReturn(Single.just(listOf()))

        presenter.onRetryConnectClicked()

        verify(router).toFeedScreen()
    }

    @Test
    fun `cafe list load failed EXPECT hide progress`() {
        whenever(getCafeListUseCase()).thenReturn(Single.error(Throwable()))
        presenter.attachView(view)

        presenter.onRetryConnectClicked()

        verify(view).hideProgress()
    }

    @Test
    fun `on takeaway info clicked EXPECT to info screen`() {
        presenter.onTakeawayInfoClicked()

        verify(router).toInfoScreen()
    }

    @Test
    fun `on back clicked EXPECT close app`() {
        presenter.attachView(view)

        presenter.onBackClicked()

        verify(view).closeApp()
    }
}