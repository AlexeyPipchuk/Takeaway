package takeaway.feature.addcafe.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import kotlinx.android.synthetic.main.add_cafe_fragment.*
import takeaway.app.*
import takeaway.feature.addcafe.domain.NewCafeValidatorField
import takeaway.feature.addcafe.presentation.AddCafePresenter
import takeaway.feature.addcafe.presentation.AddCafeView
import javax.inject.Inject

class AddCafeFragment : BaseFragment(R.layout.add_cafe_fragment), AddCafeView {

    companion object {
        fun getInstance(): Fragment = AddCafeFragment()
    }

    @Inject
    lateinit var presenter: AddCafePresenter

    private var snackbar: Snackbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initToolbar()
        initListeners()
        setTextChangedListeners()
        setTextFocusChangeListeners()
    }

    private fun initToolbar() {
        toolbar.setBackButtonListener {
            presenter.onBackClicked()
        }
    }

    private fun initListeners() {
        sendAddNewCafeRequestButton.setOnClickListener {
            presenter.onSendAddNewCafeClicked()
        }

        addBackPressedListener {
            presenter.onBackClicked()
        }
    }

    private fun setTextChangedListeners() {
        cafeNameEditText.doAfterTextChanged { cafeNameEditTextLayout.invalidateError() }
        emailEditText.doAfterTextChanged { emailEditTextLayout.invalidateError() }
        phoneEditText.doAfterTextChanged { phoneEditTextLayout.invalidateError() }
    }

    private fun setTextFocusChangeListeners() {
        val focusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                onFocusLost(view)
            }
        }

        cafeNameEditText.onFocusChangeListener = focusChangeListener
        emailEditText.onFocusChangeListener = focusChangeListener
        phoneEditText.onFocusChangeListener = focusChangeListener
    }

    private fun onFocusLost(view: View) {
        when (view) {
            !is EditText -> Unit
            cafeNameEditText -> view.onFocusLost(NewCafeValidatorField.NAME)
            emailEditText -> view.onFocusLost(NewCafeValidatorField.EMAIL)
            phoneEditText -> view.onFocusLost(NewCafeValidatorField.PHONE)
        }
    }

    override fun setPhoneMask(mask: String) {
        MaskedTextChangedListener.installOn(
            phoneEditText,
            "+$mask ([000]) [000]-[00]-[00]",
            emptyList(),
            AffinityCalculationStrategy.PREFIX
        )
    }

    override fun showProgress() {
        content.isVisible = false
        progressBar.isVisible = true
    }

    override fun hideProgress() {
        content.isVisible = true
        progressBar.isVisible = false
    }

    override fun clearFields() {
        cafeNameEditText.text?.clear()
        emailEditText.text?.clear()
        phoneEditText.text?.clear()
    }

    private fun EditText.onFocusLost(field: NewCafeValidatorField) {
        presenter.onInputFieldFocusLost(this.text.toString().trim(), field)
    }

    override fun clearFocus() {
        requireActivity().currentFocus?.clearFocus()
    }

    override fun setNameValidationResult(error: String?) {
        setFieldValidationResult(cafeNameEditTextLayout, error)
    }

    override fun setEmailValidationResult(error: String?) {
        setFieldValidationResult(emailEditTextLayout, error)
    }

    override fun setPhoneValidationResult(error: String?) {
        setFieldValidationResult(phoneEditTextLayout, error)
    }

    private fun setFieldValidationResult(layout: TextInputLayout, error: String?) {
        when {
            layout.error == null && error != null -> layout.error = error
            layout.error != null && error == null -> layout.invalidateError()
            else -> Unit
        }
    }

    override fun showSuccessSend() {
        snackbar = makeSnackbar(snackBarParent, R.string.send_add_new_cafe_request_snackbar_success)
        snackbar?.show()
    }

    override fun showFailSend() {
        snackbar = makeSnackbar(snackBarParent, R.string.send_add_new_cafe_request_snackbar_fail)
            ?.setAction(getString(R.string.send_add_new_cafe_request_snackbar_repeat)) {
                presenter.onSendAddNewCafeRepeatClick()
            }
        snackbar?.show()
    }

    override fun disableSendButton() {
        sendAddNewCafeRequestButton.disable()
    }

    override fun onDestroyView() {
        snackbar?.dismiss()
        super.onDestroyView()
        presenter.detachView()
    }
}