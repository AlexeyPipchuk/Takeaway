package takeaway.feature_feed.promo.presentation

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PromoPresenterTest {

    private val view: PromoView = mock()
    private val presenter = PromoPresenter()

    @Test
    fun `view attached EXPECT show motivation text`() {
        presenter.attachView(view)

        verify(view).showMotivationText()
    }

    @Test
    fun `on exit clicked EXPECT close dialog`() {
        presenter.attachView(view)

        presenter.onExitButtonClicked()

        verify(view).closeDialog()
    }
}