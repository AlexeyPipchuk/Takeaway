package takeaway.feature_add_cafe.presentation

import base.BasePresenter
import takeaway.shared_phone_prefix.domain.usecase.GetPhoneCountryPrefixUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import takeaway.component_validation.domain.usecase.ValidateCommonStringUseCase
import takeaway.component_validation.domain.usecase.ValidateEmailUseCase
import takeaway.component_validation.domain.usecase.ValidatePhoneUseCase
import takeaway.feature_add_cafe.domain.entity.NewCafeValidatorField
import takeaway.feature_add_cafe.domain.entity.NewCafeRequest
import takeaway.feature_add_cafe.domain.usecase.SendAddNewCafeRequestUseCase
import javax.inject.Inject

class AddCafePresenter @Inject constructor(
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateCommonStringUseCase: ValidateCommonStringUseCase,
    private val getPhoneCountryPrefixUseCase: GetPhoneCountryPrefixUseCase,
    private val sendAddNewCafeRequestUseCase: SendAddNewCafeRequestUseCase,
    private val router: AddCafeRouter
) : BasePresenter<AddCafeView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.setPhoneMask(getPhoneCountryPrefixUseCase())
    }

    private var newCafeRequest = NewCafeRequest("", "", "")

    fun onBackClicked() {
        view?.clearFields()
        router.back()
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