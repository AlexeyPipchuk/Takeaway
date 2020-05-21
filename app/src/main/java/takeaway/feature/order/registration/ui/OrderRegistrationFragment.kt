package takeaway.feature.order.registration.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.order_registration_appbar.view.*
import kotlinx.android.synthetic.main.order_registration_fragment.*
import takeaway.app.*
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.feature.order.registration.domain.entity.OrderValidatorField
import takeaway.feature.order.registration.presentation.OrderRegistrationPresenter
import takeaway.feature.order.registration.presentation.OrderRegistrationView
import takeaway.shared.order.registration.domain.entity.OrderSketch
import javax.inject.Inject

private const val ORDER_SKETCH_ARG = "ORDER_SKETCH"
var Bundle.orderSketch: OrderSketch
    get() = getSerializable(ORDER_SKETCH_ARG) as OrderSketch
    set(value) = putSerializable(ORDER_SKETCH_ARG, value)

class OrderRegistrationFragment : BaseFragment(R.layout.order_registration_fragment),
    OrderRegistrationView {

    companion object {
        fun getInstance(orderSketch: OrderSketch): Fragment = OrderRegistrationFragment()
            .apply {
                arguments = Bundle().apply {
                    this.orderSketch = orderSketch
                }
            }
    }

    @Inject
    lateinit var presenter: OrderRegistrationPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
        setTextChangedListeners()
        setTextFocusChangeListeners()

        //default focus
        nameEditText.requestFocus()
    }

    private fun initListeners() {
        orderRegistrationAppbar.backToBasketButton.setOnClickListener {
            presenter.onBackToBasketButtonClicked()
        }
        createOrderButton.setOnClickListener {
            presenter.onCreateOrderButtonClicked()
        }
        privacyPolicyLink.setOnClickListener {
            presenter.onPrivacyPolicyClicked()
        }
        takeawayRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.onTakeawayRadioButtonClicked()
                deliveryRadioButton.isChecked = false
            }
        }
        deliveryRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.onDeliveryRadioButtonClicked()
                takeawayRadioButton.isChecked = false
            }
        }

        addressTakeawayEditText.setOnClickListener {
            presenter.onAddressTakeawayClicked()
        }
        timeTakeawayEditText.setOnClickListener {
            presenter.onTimeTakeawayClicked()
        }
        timeDeliveryEditText.setOnClickListener {
            presenter.onTimeDeliveryClicked()
        }
    }

    private fun setTextChangedListeners() {
        nameEditText.doAfterTextChanged { nameEditTextLayout.invalidateError() }
        phoneEditText.doAfterTextChanged { phoneEditTextLayout.invalidateError() }
        emailEditText.doAfterTextChanged { emailEditTextLayout.invalidateError() }
        timeTakeawayEditText.doAfterTextChanged { timeTakeawayEditTextLayout.invalidateError() }
        timeDeliveryEditText.doAfterTextChanged { timeDeliveryEditTextLayout.invalidateError() }
    }

    private fun setTextFocusChangeListeners() {
        val focusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                onFocusLost(view)
            }
        }

        nameEditText.onFocusChangeListener = focusChangeListener
        phoneEditText.onFocusChangeListener = focusChangeListener
        emailEditText.onFocusChangeListener = focusChangeListener
    }

    private fun onFocusLost(view: View) {
        when (view) {
            !is EditText -> Unit
            nameEditText -> view.onFocusLost(OrderValidatorField.NAME)
            phoneEditText -> view.onFocusLost(OrderValidatorField.PHONE)
            emailEditText -> view.onFocusLost(OrderValidatorField.EMAIL)
        }
    }

    private fun EditText.onFocusLost(field: OrderValidatorField) {
        presenter.onInputFieldFocusLost(this.text.toString().trim(), field)
    }

    override fun showProgress() {
        content.isVisible = false
        progressBar.isVisible = true
    }

    override fun hideProgress() {
        content.isVisible = true
        progressBar.isVisible = false
    }

    override fun setPrivacyPolicyText() {
        privacyPolicyLink.text = getString(R.string.privacy_policy_link_text).fromHtml()
    }

    override fun showPopUpWithAddresses(addresses: List<String>) {
        addressTakeawayEditText.showPopup(addresses) {
            presenter.onAddressSelected(it)
        }
    }

    override fun showPopUpWithAvailableTakeawayTimes(times: List<String>) {
        timeTakeawayEditText.showPopup(times) {
            presenter.onTakeawayTimeSelected(it)
        }
    }

    override fun showPopUpWithAvailableDeliveryTimes(times: List<String>) {
        timeDeliveryEditText.showPopup(times) {
            presenter.onDeliveryTimeSelected(it)
        }
    }

    override fun setAddress(address: String) {
        addressTakeawayEditText.setText(address)
    }

    override fun setTakeawayTime(takeawayTime: String) {
        timeTakeawayEditText.setText(takeawayTime)
    }

    override fun setDeliveryTime(deliveryTime: String) {
        timeDeliveryEditText.setText(deliveryTime)
    }

    override fun showOrderCafeInfo(cafe: Cafe) {
        orderCafeName.text = cafe.name

        val cafeLogoImg = cafe.logoUrl
        if (cafeLogoImg != null) {
            orderCafeLogo.isVisible = true
            orderCafeLogoImg.loadImage(cafeLogoImg)
        }
    }

    override fun selectTakeawayReceivingMethod(severalAddresses: Boolean) {
        takeawayOptionLayout.isVisible = true
        deliveryOptionLayout.isVisible = false

        if (severalAddresses) {
            addressTakeawayEditTextLayout.isVisible = true
        }
    }

    override fun selectDeliveryReceivingMethod() {
        deliveryOptionLayout.isVisible = true
        takeawayOptionLayout.isVisible = false
    }

    override fun showBasketAmount(basketAmountWithoutAll: Int) {
        orderAmountPrice.text =
            getString(R.string.rubles_postfix).format(basketAmountWithoutAll.toString())
    }

    override fun showTakeawayRadioButtonSubtitle(takeawayDiscount: Int) {
        takeawayRadioButtonSubtitle.text =
            getString(R.string.takeaway_radio_button_subtitle).format(takeawayDiscount.toString())
    }

    override fun showDeliveryValueToFreeDescription(valueToFreeDelivery: Int) {
        deliveryRadioButtonSubtitle.text =
            getString(R.string.message_with_value_to_free_delivery).format(valueToFreeDelivery.toString())
    }

    override fun showDeliveryMinSumDescription(deliveryMinSum: Int) {
        deliveryRadioButtonSubtitle.text =
            getString(R.string.minimum_amount_for_free_delivery_message).format(deliveryMinSum.toString())
    }

    override fun setTakeawayDiscountResult(takeawayDiscount: Int, takeawayDiscountCalculated: Int) {
        takeawayDiscountCalculatedText.text =
            getString(R.string.order_registration_takeaway_discount_text).format(takeawayDiscount.toString())

        takeawayDiscountCalculatedAmount.text =
            getString(R.string.rubles_postfix).format(takeawayDiscountCalculated.toString())
    }

    override fun showTakeawayDiscountResult() {
        takeawayOrderLayout.isVisible = true
    }

    override fun hideTakeawayDiscountResult() {
        takeawayOrderLayout.isVisible = false
    }

    override fun setDeliveryCostResult(takeawayDeliveryCalculated: Int) {
        deliveryPriceCalculatedAmount.text =
            getString(R.string.rubles_postfix).format(takeawayDeliveryCalculated.toString())
    }

    override fun showDeliveryCostResult() {
        deliveryOrderLayout.isVisible = true
    }

    override fun hideDeliveryCostResult() {
        deliveryOrderLayout.isVisible = false
    }

    override fun showOrderAmountWithTakeaway(orderWithTakeawayAmount: Int) {
        orderResultAmountPrice.text =
            getString(R.string.rubles_postfix).format(orderWithTakeawayAmount.toString())
    }

    override fun showOrderAmountWithDelivery(orderWithDeliveryAmount: Int) {
        orderResultAmountPrice.text =
            getString(R.string.rubles_postfix).format(orderWithDeliveryAmount.toString())
    }

    override fun disableResultAndButton() {
        orderResultAmountTitle.isVisible = false
        orderResultAmountPrice.isVisible = false
        orderSeparator.isVisible = false
        createOrderButton.disable()
    }

    override fun enableResultAndButton() {
        orderResultAmountTitle.isVisible = true
        orderResultAmountPrice.isVisible = true
        orderSeparator.isVisible = true
        createOrderButton.enable()
    }

    override fun clearFocus() {
        requireActivity().currentFocus?.clearFocus()
    }

    override fun setNameValidationResult(error: String?) {
        setFieldValidationResult(nameEditTextLayout, error)
    }

    override fun setPhoneValidationResult(error: String?) {
        setFieldValidationResult(phoneEditTextLayout, error)
    }

    override fun setEmailValidationResult(error: String?) {
        setFieldValidationResult(emailEditTextLayout, error)
    }

    override fun setStreetValidationResult(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setHouseNumberValidationResult(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPorchValidationResult(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFloorValidationResult(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFlatValidationResult(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCommentValidationResult(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTakeawayTimeValidationResult(error: String?) {
        setFieldValidationResult(timeTakeawayEditTextLayout, error)
    }

    override fun setDeliveryTimeValidationResult(error: String?) {
        setFieldValidationResult(timeDeliveryEditTextLayout, error)
    }

    private fun setFieldValidationResult(layout: TextInputLayout, error: String?) {
        when {
            layout.error == null && error != null -> layout.error = error
            layout.error != null && error == null -> layout.invalidateError()
            else -> Unit
        }
    }

    override fun requestFocusOnFirstError() {
        requestFocusOnFirstError(formLayout = basketContent)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }
}