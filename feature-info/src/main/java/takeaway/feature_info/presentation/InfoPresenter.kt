package takeaway.feature_info.presentation

import base.BasePresenter
import javax.inject.Inject

class InfoPresenter @Inject constructor(
    private val router: InfoRouter
) : BasePresenter<InfoView>() {

    fun onBackClicked() {
        router.backToStartPoint()
    }

    fun onPrivacyPolicyClicked() {
        router.toPrivacyPolicy()
    }
}