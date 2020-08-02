package takeaway.feature_confirmation.presentation

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConfirmationPresenterTest {

    private val view: ConfirmationView = mock()
    private val router: ConfirmationRouter = mock()
    private val orderId = "123"

    private val presenter = ConfirmationPresenter(router, orderId)

    @Test
    fun `view attached EXPECT set order id`() {
        presenter.attachView(view)

        verify(view).setOrderId(orderId)
    }

    @Test
    fun `to main page back clicked EXPECT route to feed screen`() {
        presenter.onToMainPageBackClicked()

        verify(router).toFeedScreen()
    }

    @Test
    fun `back clicked EXPECT route to feed screen`() {
        presenter.onBackClicked()

        verify(router).toFeedScreen()
    }
}