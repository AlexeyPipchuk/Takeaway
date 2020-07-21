package takeaway.feature_add_cafe.presentation

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import domain.usecase.GetPhoneCountryPrefixUseCase
import io.reactivex.Completable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.component_test.TestSchedulerRule
import takeaway.component_validation.domain.usecase.ValidateCommonStringUseCase
import takeaway.component_validation.domain.usecase.ValidateEmailUseCase
import takeaway.component_validation.domain.usecase.ValidatePhoneUseCase
import takeaway.feature_add_cafe.domain.entity.NewCafeRequest
import takeaway.feature_add_cafe.domain.entity.NewCafeValidatorField
import takeaway.feature_add_cafe.domain.usecase.SendAddNewCafeRequestUseCase

@RunWith(MockitoJUnitRunner::class)
class AddCafePresenterTest {

    @Rule
    @JvmField
    val testRule = TestSchedulerRule()

    private val validatePhoneUseCase: ValidatePhoneUseCase = mock()
    private val validateEmailUseCase: ValidateEmailUseCase = mock()
    private val validateCommonStringUseCase: ValidateCommonStringUseCase = mock()
    private val getPhoneCountryPrefixUseCase: GetPhoneCountryPrefixUseCase = mock()
    private val sendAddNewCafeRequestUseCase: SendAddNewCafeRequestUseCase = mock()
    private val router: AddCafeRouter = mock()

    private val view: AddCafeView = mock()
    private val newCafeRequest = NewCafeRequest("", "", "")

    private val presenter = AddCafePresenter(
        validatePhoneUseCase,
        validateEmailUseCase,
        validateCommonStringUseCase,
        getPhoneCountryPrefixUseCase,
        sendAddNewCafeRequestUseCase,
        router
    )

    @Test
    fun `view attached EXPECT set phone mask`() {
        val phoneMask = "+7"
        whenever(getPhoneCountryPrefixUseCase()).thenReturn(phoneMask)

        presenter.attachView(view)

        verify(getPhoneCountryPrefixUseCase).invoke()
        verify(view).setPhoneMask(phoneMask)
    }

    @Test
    fun `back clicked EXPECT clear fields`() {
        presenter.attachView(view)

        presenter.onBackClicked()

        verify(view).clearFields()
    }

    @Test
    fun `on cafe name field focus lost EXPECT set name validation result`() {
        val validationResult = null
        val cafeName = "Foo"
        whenever(validateCommonStringUseCase(cafeName, required = true))
            .thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(cafeName, NewCafeValidatorField.NAME)

        verify(validateCommonStringUseCase).invoke(cafeName, required = true)
        verify(view).setNameValidationResult(validationResult)
    }

    @Test
    fun `on email field focus lost EXPECT set email validation result`() {
        val validationResult = null
        val email = "Foo"
        whenever(validateEmailUseCase(email)).thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(email, NewCafeValidatorField.EMAIL)

        verify(validateEmailUseCase).invoke(email)
        verify(view).setEmailValidationResult(validationResult)
    }

    @Test
    fun `on phone field focus lost EXPECT set phone validation result`() {
        val validationResult = null
        val phone = "Foo"
        whenever(validatePhoneUseCase(phone))
            .thenReturn(validationResult)
        presenter.attachView(view)

        presenter.onInputFieldFocusLost(phone, NewCafeValidatorField.PHONE)

        verify(validatePhoneUseCase).invoke(phone)
        verify(view).setPhoneValidationResult(validationResult)
    }

    @Test
    fun `send add new cafe clicked EXPECT clear focus`() {
        whenever(sendAddNewCafeRequestUseCase(any())).thenReturn(Completable.never())
        presenter.attachView(view)

        presenter.onSendAddNewCafeClicked()

        verify(view).clearFocus()
    }

    @Test
    fun `send add new cafe clicked EXPECT validate name`() {
        val validationResult = null
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(validationResult)
        whenever(sendAddNewCafeRequestUseCase(any())).thenReturn(Completable.never())
        presenter.attachView(view)

        presenter.onSendAddNewCafeClicked()

        verify(validateCommonStringUseCase).invoke(any(), any())
        verify(view).setNameValidationResult(validationResult)
    }

    @Test
    fun `send add new cafe clicked EXPECT validate email`() {
        val validationResult = null
        whenever(validateEmailUseCase(any())).thenReturn(validationResult)
        whenever(sendAddNewCafeRequestUseCase(any())).thenReturn(Completable.never())
        presenter.attachView(view)

        presenter.onSendAddNewCafeClicked()

        verify(validateEmailUseCase).invoke(any())
        verify(view).setEmailValidationResult(validationResult)
    }

    @Test
    fun `send add new cafe clicked EXPECT validate phone`() {
        val validationResult = null
        whenever(validatePhoneUseCase(any())).thenReturn(validationResult)
        whenever(sendAddNewCafeRequestUseCase(any())).thenReturn(Completable.never())
        presenter.attachView(view)

        presenter.onSendAddNewCafeClicked()

        verify(validatePhoneUseCase).invoke(any())
        verify(view).setPhoneValidationResult(validationResult)
    }

    @Test
    fun `send add new cafe EXPECT show progress`() {
        val validationResult = null
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(validationResult)
        whenever(validateEmailUseCase(any())).thenReturn(validationResult)
        whenever(validatePhoneUseCase(any())).thenReturn(validationResult)
        whenever(sendAddNewCafeRequestUseCase(any())).thenReturn(Completable.never())
        presenter.attachView(view)

        presenter.onSendAddNewCafeClicked()

        verify(view).showProgress()
    }

    @Test
    fun `add new cafe request sent EXPECT hide progress`() {
        val validationResult = null
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(validationResult)
        whenever(validateEmailUseCase(any())).thenReturn(validationResult)
        whenever(validatePhoneUseCase(any())).thenReturn(validationResult)
        whenever(sendAddNewCafeRequestUseCase(newCafeRequest)).thenReturn(Completable.complete())
        presenter.attachView(view)

        presenter.onSendAddNewCafeClicked()

        verify(view).hideProgress()
    }

    @Test
    fun `add new cafe request sent EXPECT show success sent`() {
        val validationResult = null
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(validationResult)
        whenever(validateEmailUseCase(any())).thenReturn(validationResult)
        whenever(validatePhoneUseCase(any())).thenReturn(validationResult)
        whenever(sendAddNewCafeRequestUseCase(newCafeRequest)).thenReturn(Completable.complete())
        presenter.attachView(view)

        presenter.onSendAddNewCafeClicked()

        verify(view).showSuccessSend()
    }

    @Test
    fun `add new cafe request sent EXPECT disable send button`() {
        val validationResult = null
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(validationResult)
        whenever(validateEmailUseCase(any())).thenReturn(validationResult)
        whenever(validatePhoneUseCase(any())).thenReturn(validationResult)
        whenever(sendAddNewCafeRequestUseCase(newCafeRequest)).thenReturn(Completable.complete())
        presenter.attachView(view)

        presenter.onSendAddNewCafeClicked()

        verify(view).disableSendButton()
    }

    @Test
    fun `add new cafe request failed EXPECT hide progress`() {
        val validationResult = null
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(validationResult)
        whenever(validateEmailUseCase(any())).thenReturn(validationResult)
        whenever(validatePhoneUseCase(any())).thenReturn(validationResult)
        whenever(sendAddNewCafeRequestUseCase(newCafeRequest)).thenReturn(Completable.error(Throwable()))
        presenter.attachView(view)

        presenter.onSendAddNewCafeClicked()

        verify(view).hideProgress()
    }

    @Test
    fun `add new cafe request failed EXPECT show fail sent`() {
        val validationResult = null
        whenever(validateCommonStringUseCase(any(), any())).thenReturn(validationResult)
        whenever(validateEmailUseCase(any())).thenReturn(validationResult)
        whenever(validatePhoneUseCase(any())).thenReturn(validationResult)
        whenever(sendAddNewCafeRequestUseCase(newCafeRequest)).thenReturn(Completable.error(Throwable()))
        presenter.attachView(view)

        presenter.onSendAddNewCafeClicked()

        verify(view).showFailSend()
    }
}