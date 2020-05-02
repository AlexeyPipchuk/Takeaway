package takeaway.feature.cafe.ui.product

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.takeaway.R
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.custom_counter_view.view.*
import kotlinx.android.synthetic.main.product_dialog_fragment.*
import takeaway.app.BaseDialogFragment
import takeaway.app.loadImage
import takeaway.feature.cafe.domain.entity.Product
import takeaway.feature.cafe.presentation.ProductPresenter
import javax.inject.Inject

private const val PRODUCT_ARG = "PRODUCT"
var Bundle.product: Product
    get() = getSerializable(PRODUCT_ARG) as Product
    set(value) = putSerializable(PRODUCT_ARG, value)

class ProductDialogFragment : BaseDialogFragment(R.layout.product_dialog_fragment), ProductView {

    companion object {
        fun getInstance(product: Product): DialogFragment = ProductDialogFragment()
            .apply {
                arguments = Bundle().apply {
                    this.product = product
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

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun showProductInfo(product: Product) {
        if (product.imgUrl != null) {
            productImg.isVisible = true
            //TODO(Решить проблему с закруглением картинки)
            productImg.loadImage(product.imgUrl)
        }

        productName.text = product.title
        productDescription.text = product.description
        productWeight.text = getString(R.string.product_weight).format(product.weight)
        productAmount.text = getString(R.string.rubles_postfix).format(product.price)
    }

    private fun MaterialButton.enable() {
        isEnabled = true
        background.setTint(getColor(requireContext(), R.color.colorAccent))
        setTextColor(getColor(requireContext(), R.color.backgroundMain))
    }

    private fun MaterialButton.disable() {
        isEnabled = false
        background.setTint(getColor(requireContext(), R.color.material_on_background_disabled))
        setTextColor(getColor(requireContext(), R.color.black))
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

    override fun closeDialog() {
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

    private fun String.cutCurrency(): String =
        this.replace(ROUBLE_CURRENCY_POSTFIX, "")
}