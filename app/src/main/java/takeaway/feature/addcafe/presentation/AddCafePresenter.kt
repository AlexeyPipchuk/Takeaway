package takeaway.feature.addcafe.presentation

import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import javax.inject.Inject

class AddCafePresenter @Inject constructor(
    private val router: Router
) : BasePresenter<AddCafeView>() {

    fun onBackClicked() {
        router.exit()
    }

    fun onSendAddNewCafeClicked() {

    }

    fun onSendAddNewCafeRepeatClick() {

    }

    private fun onSendFailed() {
        view?.showFailSend()
    }

    private fun onSendSuccess() {
        view?.showSuccessSend()
        view?.disableSendButton()
    }
}