package takeaway.feature_info.presentation

import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InfoPresenterTest {

    private val router: InfoRouter = mock()
    private val presenter = InfoPresenter(router)

    @Test
    fun `on back clicked EXPECT back to start point`() {
        presenter.onBackClicked()

        verify(router).backToStartPoint()
    }

    @Test
    fun `on privacy policy clicked EXPECT route to privacy policy`() {
        presenter.onPrivacyPolicyClicked()

        verify(router).toPrivacyPolicy()
    }
}