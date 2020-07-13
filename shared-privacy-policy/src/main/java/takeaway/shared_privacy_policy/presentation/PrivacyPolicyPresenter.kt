package takeaway.shared_privacy_policy.presentation

import base.BasePresenter
import javax.inject.Inject

class PrivacyPolicyPresenter @Inject constructor(
    private val router: PrivacyPolicyRouter
) : BasePresenter<PrivacyPolicyView>() {

    fun onBackClicked() {
        router.back()
    }
}