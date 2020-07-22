package takeaway.shared_privacy_policy.presentation

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PrivacyPolicyPresenterTest {

    private val router: PrivacyPolicyRouter = mock()
    private val presenter = PrivacyPolicyPresenter(router)

    @Test
    fun `back clicked EXPECT route back`() {
        presenter.onBackClicked()

        verify(router).back()
    }
}