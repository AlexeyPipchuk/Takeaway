package takeaway.feature.info.presentation

import ru.terrakok.cicerone.Router
import base.BasePresenter
import takeaway.app.navigation.Screen
import takeaway.feature.info.ui.InfoView
import javax.inject.Inject

class InfoPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<InfoView>() {

    fun onBackClicked() {
        router.backTo(null)
    }

    fun onPrivacyPolicyClicked() {
        router.navigateTo(Screen.PrivacyPolicyScreen)
    }
}