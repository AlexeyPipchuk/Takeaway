package takeaway.feature_success.presentation

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SuccessPresenterTest {

    private val router: SuccessRouter = mock()
    private val orderId = "123"
    private val view: SuccessView = mock()
    private val presenter = SuccessPresenter(router, orderId)

    @Test
    fun `view attached EXPECT set order id`() {
        presenter.attachView(view)

        verify(view).setOrderId(orderId)
    }

    @Test
    fun `feed button clicked EXPECT route to feed`() {
        presenter.onFeedButtonClicked()

        verify(router).toFeedScreen()
    }

    @Test
    fun `on back clicked EXPECT route to feed`() {
        presenter.onBackClicked()

        verify(router).toFeedScreen()
    }
}