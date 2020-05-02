package takeaway.feature.cafe.ui.product

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.takeaway.R
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
    }

    @Inject
    lateinit var presenter: ProductPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
    }

    private fun initListeners() {
        exitButton.setOnClickListener { dismiss() }
        toBasketButton.setOnClickListener {
            targetFragment?.onActivityResult(targetRequestCode, 1, null)
            dismiss()
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
}