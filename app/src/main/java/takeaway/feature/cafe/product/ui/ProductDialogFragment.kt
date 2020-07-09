package takeaway.feature.cafe.product.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.takeaway.R
import com.google.android.material.button.MaterialButton
import domain.entity.Product
import kotlinx.android.synthetic.main.custom_counter_view.view.*
import kotlinx.android.synthetic.main.product_dialog_fragment.*
import takeaway.app.BaseDialogFragment
import takeaway.app.disable
import takeaway.app.enable
import takeaway.app.loadImage
import takeaway.feature.cafe.product.presentation.ProductPresenter
import takeaway.feature.cafe.product.presentation.ProductView
import takeaway.feature.cafe.product.severalcafe.SeveralCafeWarningDialogFragment
import takeaway.feature.cafe.product.severalcafe.SeveralCafeWarningDialogFragment.Companion.ACCEPT_RESULT
import takeaway.shared_cafe.domain.entity.Cafe
import javax.inject.Inject

private const val PRODUCT_ARG = "PRODUCT"
var Bundle.product: Product
    get() = getSerializable(PRODUCT_ARG) as Product
    set(value) = putSerializable(PRODUCT_ARG, value)

private const val CAFE_ARG = "CAFE"
var Bundle.cafe: Cafe
    get() = getSerializable(CAFE_ARG) as Cafe
    set(value) = putSerializable(CAFE_ARG, value)

class ProductDialogFragment : BaseDialogFragment(R.layout.product_dialog_fragment),
    ProductView {

    companion object {
        fun getInstance(product: Product, cafe: Cafe): DialogFragment =
            ProductDialogFragment()
                .apply {
                    arguments = Bundle().apply {
                        this.product = product
                        this.cafe = cafe
                    }
                }

        const val MAX_COUNT = 50
        const val MIN_COUNT = 0
        const val ROUBLE_CURRENCY_POSTFIX = " \u20BD"
    }

    @Inject
    lateinit var presenter: ProductPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
    }

    private fun initListeners() {
        exitButton.setOnClickListener { presenter.onExitButtonClicked() }
        toBasketButton.setOnClickListener {
            presenter.onToBasketButtonClicked(
                productCounter.productCount.text.toString().toInt()
            )
        }

        productCounter.minusButton.setOnClickListener {
            val currentCount = productCounter.productCount.text.toString().toInt()
            val wantedCount = currentCount.dec()
            if (currentCount >= MIN_COUNT) {
                productCounter.productCount.text = wantedCount.toString()
                presenter.onMinusButtonClicked()
            }

            when {
                wantedCount == MIN_COUNT -> {
                    productCounter.minusButton.disableMathOperation()
                    toBasketButton.disable()
                }

                currentCount == MAX_COUNT -> {
                    productCounter.plusButton.enableMathOperation()
                }
            }
        }

        productCounter.plusButton.setOnClickListener {
            val currentCount = productCounter.productCount.text.toString().toInt()
            val wantedCount = currentCount.inc()
            if (currentCount < MAX_COUNT) {
                productCounter.productCount.text = wantedCount.toString()
                presenter.onPlusButtonClicked()
            }

            when {
                wantedCount == MAX_COUNT -> {
                    productCounter.plusButton.disableMathOperation()
                }

                currentCount == MIN_COUNT -> {
                    productCounter.minusButton.enableMathOperation()
                    toBasketButton.enable()
                }

                else -> Unit
            }
        }
    }

    override fun showProductInfo(product: Product) {
        product.imgUrl?.let { url ->
            productImg.isVisible = true
            productImg.loadImage(url)
        }

        productName.text = product.title
        productDescription.text = product.description
        productWeight.text = getString(R.string.product_weight).format(product.weight)
        productAmount.text = getString(R.string.rubles_postfix).format(product.price)
    }

    private fun MaterialButton.disableMathOperation() {
        isEnabled = false
        setTextColor(
            getColor(
                requireContext(),
                R.color.material_on_primary_disabled
            )
        )
    }

    private fun MaterialButton.enableMathOperation() {
        isEnabled = true
        setTextColor(
            getColor(
                requireContext(),
                R.color.black
            )
        )
    }

    override fun closeProductDialog() {
        targetFragment?.onActivityResult(targetRequestCode, 1, null)
        dismiss()
    }

    override fun incPrice(price: Int) {
        val newPrice = productAmount.text.toString().cutCurrency().toInt() + price
        productAmount.text = getString(R.string.rubles_postfix).format(newPrice.toString())
    }

    override fun decPrice(price: Int) {
        val newPrice = productAmount.text.toString().cutCurrency().toInt() - price
        productAmount.text = getString(R.string.rubles_postfix).format(newPrice.toString())
    }

    override fun showClearBasketQuestionDialog() {
        val severalCafeWarningDialog = SeveralCafeWarningDialogFragment()
        severalCafeWarningDialog.setTargetFragment(this, 0)
        fragmentManager?.let { fragmentManager ->
            severalCafeWarningDialog.show(
                fragmentManager,
                severalCafeWarningDialog::class.java.name
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == ACCEPT_RESULT) {
            presenter.onApproveToClearBasketButtonClicked(productCounter.productCount.text.toString().toInt())
        }
    }

    private fun String.cutCurrency(): String =
        this.replace(ROUBLE_CURRENCY_POSTFIX, "")

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}