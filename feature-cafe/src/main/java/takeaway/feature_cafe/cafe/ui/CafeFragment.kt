package takeaway.feature_cafe.cafe.ui

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import base.BaseFragment
import domain.entity.Product
import kotlinx.android.synthetic.main.cafe_appbar.view.*
import kotlinx.android.synthetic.main.cafe_fragment.*
import takeaway.component_ui.extensions.fromHtml
import takeaway.component_ui.extensions.loadImage
import takeaway.feature_cafe.R
import takeaway.feature_cafe.cafe.presentation.CafePresenter
import takeaway.feature_cafe.cafe.presentation.CafeView
import takeaway.feature_cafe.cafe.presentation.model.CategoryItem
import takeaway.feature_cafe.product.ui.ProductAdapter
import takeaway.feature_cafe.product.ui.ProductDialogFragment
import takeaway.shared_cafe.domain.entity.Cafe
import takeaway.shared_error.extensions.showNoInternetDialog
import takeaway.shared_error.extensions.showServiceUnavailableDialog
import javax.inject.Inject

private const val CAFE_ARG = "CAFE"
var Bundle.cafe: Cafe
    get() = getSerializable(CAFE_ARG) as Cafe
    set(value) = putSerializable(CAFE_ARG, value)

class CafeFragment : BaseFragment(R.layout.cafe_fragment), CafeView {

    companion object {
        fun getInstance(cafe: Cafe): Fragment = CafeFragment()
            .apply {
                arguments = Bundle().apply {
                    this.cafe = cafe
                }
            }
    }

    @Inject
    lateinit var presenter: CafePresenter

    private var productAdapter: ProductAdapter? = null
    private var categoryAdapter: CategoryAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
        presenter.onScreenUpdated()
    }

    private fun initListeners() {
        cafeAppbar.backToAllCafeButton.setOnClickListener {
            presenter.onBackClicked()
        }

        cafeAppbar.basket.setOnClickListener {
            presenter.onBasketClick()
        }

        linkOnStatist.text = getString(R.string.link_on_statist).fromHtml()
        linkOnStatist.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun setProducts(productList: List<Product>) {
        initProductAdapter(productList)
    }

    override fun setCategories(categoryList: List<CategoryItem>) {
        initCategoriesAdapter(categoryList)
    }

    private fun initProductAdapter(productList: List<Product>) {
        productAdapter = ProductAdapter(
            context = requireContext(),
            onCafeClickListener = presenter::onProductClicked
        )
        productAdapter?.productList = productList

        productListRecycler.adapter = productAdapter
    }

    private fun initCategoriesAdapter(categoryList: List<CategoryItem>) {
        categoryAdapter = CategoryAdapter(
            onCategoryClickListener = presenter::onCategoryClicked
        )
        categoryAdapter?.categoryList = categoryList

        categoryListRecycler.isVisible = true
        categoryListRecycler.adapter = categoryAdapter
    }

    override fun updateCategories(categoryList: List<CategoryItem>) {
        categoryAdapter?.categoryList = categoryList
    }

    override fun updateProducts(productList: List<Product>) {
        productAdapter?.productList = productList
    }

    override fun showProgress() {
        progressBar.isVisible = true
        content.isVisible = false
    }

    override fun hideProgress() {
        progressBar.isVisible = false
        content.isVisible = true
    }

    override fun showNoInternetDialog() {
        showNoInternetDialog(
            positiveResult = {
                presenter.onRetryClicked()
            }, negativeResult = {
                presenter.onNegativeButtonClicked()
            }
        )
    }

    override fun showServiceUnavailable() {
        showServiceUnavailableDialog(
            positiveResult = {
                presenter.onRetryClicked()
            }, negativeResult = {
                presenter.onNegativeButtonClicked()
            }
        )
    }

    override fun showCafeImages(mainImg: List<String>?, logoImg: String?) {
        val cafeMainImg = mainImg?.firstOrNull()
        if (cafeMainImg != null) {
            titleCafeImg.loadImage(cafeMainImg)
        }

        if (logoImg != null) {
            cafeLogo.isVisible = true
            cafeLogo.loadImage(logoImg)
        }
    }

    override fun showCafeInfo(
        cafeType: String,
        name: String,
        description: String,
        address: String,
        workFrom: String,
        workTo: String
    ) {
        this.cafeType.text = cafeType
        this.cafeName.text = name
        this.cafeDescription.text = description
        this.location.text = address
        this.time.text = workFrom.plus("-").plus(workTo)
    }

    override fun showTakeawayDiscount(takeawayDiscount: Int) {
        this.takeawayDiscount.text =
            getString(R.string.takeaway_discount)
                .format(takeawayDiscount.toString())

        this.takeawayDiscount.isVisible = true
    }

    override fun showBusinessLunch(businessFrom: String, businessTo: String) {
        timeSubtitle.isVisible = true
        businessTimeSubtitle.isVisible = true
        businessTime.isVisible = true
        businessTime.text = businessFrom.plus("-").plus(businessTo)
    }

    override fun showDeliveryFreeFrom(freeFrom: Int) {
        deliveryFreeFrom.text = getString(R.string.delivery_free_from)
            .format(freeFrom.toString())
    }

    override fun showDeliveryPrice(deliveryPrice: Int) {
        this.deliveryPrice.text =
            getString(R.string.delivery_price).format(deliveryPrice.toString())
        this.deliveryPrice.isVisible = true
    }

    override fun showMinDeliverySum(minDeliverySum: Int) {
        minimumDeliverySumText.text = getString(R.string.minimumDeliverySumText)
            .format(minDeliverySum.toString())
        minimumDeliverySumText.isVisible = true
    }

    override fun showProductDialog(product: Product, cafe: Cafe) {
        val productDialog = ProductDialogFragment.getInstance(product, cafe)
        productDialog.setTargetFragment(this, 0)
        fragmentManager?.let { fragmentManager ->
            productDialog.show(
                fragmentManager,
                productDialog::class.java.name
            )
        }
    }

    override fun setBasketAmount(basketAmount: Int) {
        cafeAppbar.basketAmountText.text =
            getString(R.string.rubles_postfix).format(basketAmount.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onScreenUpdated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}