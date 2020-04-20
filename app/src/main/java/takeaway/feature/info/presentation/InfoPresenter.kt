package takeaway.feature.info.presentation

import takeaway.app.BasePresenter
import takeaway.feature.info.ui.InfoView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class InfoPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<InfoView>() {

    fun onBackClicked() {
        router.backTo(null)
    }
}