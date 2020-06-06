package takeaway.feature.addcafe.presentation

import takeaway.app.TakeawayView

interface AddCafeView : TakeawayView {

    fun showSuccessSend()

    fun showFailSend()

    fun clearFields()

    fun disableSendButton()

    fun setPhoneMask(mask: String)

    fun showProgress()

    fun hideProgress()

    fun clearFocus()

    fun setNameValidationResult(error: String?)

    fun setPhoneValidationResult(error: String?)

    fun setEmailValidationResult(error: String?)
}