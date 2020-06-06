package takeaway.feature.addcafe.presentation

import takeaway.app.TakeawayView

interface AddCafeView : TakeawayView {

    fun showSuccessSend()

    fun showFailSend()

    fun disableSendButton()
}