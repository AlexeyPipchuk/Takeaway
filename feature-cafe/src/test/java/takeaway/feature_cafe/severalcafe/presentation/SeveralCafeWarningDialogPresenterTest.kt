package takeaway.feature_cafe.severalcafe.presentation

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SeveralCafeWarningDialogPresenterTest {

    private val view: SeveralCafeWarningView = mock()
    private val presenter = SeveralCafeWarningDialogPresenter()

    @Test
    fun `on clear basket clicked EXPECT close dialog with accept result`() {
        presenter.attachView(view)

        presenter.onClearBasketClicked()

        verify(view).closeDialogWithAcceptResult()
    }

    @Test
    fun `on cancel clicked EXPECT close dialog with reject result`() {
        presenter.attachView(view)

        presenter.onCancelClicked()

        verify(view).closeDialogWithRejectResult()
    }
}