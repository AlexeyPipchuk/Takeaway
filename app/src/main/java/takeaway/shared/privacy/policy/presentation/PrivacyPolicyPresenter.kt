package takeaway.shared.privacy.policy.presentation

import ru.terrakok.cicerone.Router
import base.BasePresenter
import javax.inject.Inject

class PrivacyPolicyPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<PrivacyPolicyView>() {

    fun onBackClicked() {
        router.exit()
    }
}