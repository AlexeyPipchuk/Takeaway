package takeaway.feature.addcafe.presentation

import base.BasePresenter
import domain.usecase.GetPhoneCountryPrefixUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router
import takeaway.feature.addcafe.domain.NewCafeValidatorField
import takeaway.feature.addcafe.domain.entity.NewCafeRequest
import takeaway.feature.addcafe.domain.usecase.SendAddNewCafeRequestUseCase
import takeaway.feature.order.registration.domain.usecase.validation.ValidateCommonStringUseCase
import takeaway.feature.order.registration.domain.usecase.validation.ValidateEmailUseCase
import takeaway.feature.order.registration.domain.usecase.validation.ValidatePhoneUseCase
import javax.inject.Inject

class AddCafePresenter @Inject constructor(
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateCommonStringUseCase: ValidateCommonStringUseCase,
    private val getPhoneCountryPrefixUseCase: GetPhoneCountryPrefixUseCase,
    private val sendAddNewCafeRequestUseCase: SendAddNewCafeRequestUseCase,
    private val router: Router
) : BasePresenter<AddCafeView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.setPhoneMask(getPhoneCountryPrefixUseCase())
    }

    private var newCafeRequest = NewCafeRequest("", "", "")

    fun onBackClicked() {
        view?.clearFields()
        router.exit()
    }

    fun onInputFieldFocusLost(value: String, field: NewCafeValidatorField) {
        when (field) {

            NewCafeValidatorField.NAME -> {
                newCafeRequest = newCafeRequest.copy(name = value)
                validateName()
            }

            NewCafeValidatorField.EMAIL -> {
                newCafeRequest = newCafeRequest.copy(email = value)
                validateEmail()
            }

            NewCafeValidatorField.PHONE -> {
                newCafeRequest = newCafeRequest.copy(phone = value)
                validatePhone()
            }
        }
    }

    private fun validateName(): Boolean {
        val validationResult = validateCommonStringUseCase(newCafeRequest.name, required = true)
        view?.setNameValidationResult(validationResult)

        return validationResult == null
    }

    private fun validateEmail(): Boolean {
        val validationResult = validateEmailUseCase(newCafeRequest.email)
        view?.setEmailValidationResult(validationResult)

        return validationResult == null
    }

    private fun validatePhone(): Boolean {
        val validationResult = validatePhoneUseCase(newCafeRequest.phone)
        view?.setPhoneValidationResult(validationResult)

        return validationResult == null
    }

    fun onSendAddNewCafeClicked() {
        view?.clearFocus()

        val isNameValid = validateName()
        val isEmailValid = validateEmail()
        val isPhoneValid = validatePhone()

        if (
            isNameValid &&
            isPhoneValid &&
            isEmailValid
        ) {
            sendAddNewCafeRequest()
        }
    }

    private fun sendAddNewCafeRequest() {
        view?.showProgress()

        sendAddNewCafeRequestUseCase(newCafeRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.hideProgress()
                    onSendSuccess()
                },
                {
                    view?.hideProgress()
                    onSendFailed()
                }
            )
            .addToDisposable()
    }

    fun onSendAddNewCafeRepeatClick() {
        sendAddNewCafeRequest()
    }

    private fun onSendFailed() {
        view?.showFailSend()
    }

    private fun onSendSuccess() {
        view?.showSuccessSend()
        view?.disableSendButton()
    }
}