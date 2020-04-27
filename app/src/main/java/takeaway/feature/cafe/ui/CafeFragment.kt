package takeaway.feature.cafe.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import kotlinx.android.synthetic.main.cafe_appbar.*
import kotlinx.android.synthetic.main.cafe_fragment.*
import takeaway.app.BaseFragment
import takeaway.app.loadImage
import takeaway.app.showNoInternetDialog
import takeaway.app.showServiceUnavailableDialog
import takeaway.feature.cafe.domain.entity.Product
import takeaway.feature.cafe.presentation.CafePresenter
import takeaway.feature.feed.domain.entity.Cafe
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

    private var adapter: ProductAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
    }

    private fun initListeners() {
        backButton.setOnClickListener {
            presenter.onBackClicked()
        }
    }

    override fun setProducts(productList: List<Product>) {
        initAdapter(productList)
    }

    private fun initAdapter(productList: List<Product>) {
        adapter = ProductAdapter(
            context = requireContext(),
            onCafeClickListener = presenter::onProductClicked
        )
        adapter?.productList = productList

        productListRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        //TODO(Внести в базу каким нибудь образом)
        presenter.detachView()
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
                //TODO(Заглушка с возможностью повторить)
            }
        )
    }

    override fun showServiceUnavailable() {
        showServiceUnavailableDialog(
            positiveResult = {
                presenter.onRetryClicked()
            }, negativeResult = {
                //TODO(Заглушка с возможностью повторить)
            }
        )
    }

    override fun showCafeInfo(cafe: Cafe) {

        val cafeMainImg = cafe.imgUrls?.firstOrNull()
        if (cafeMainImg != null) {
            titleCafeImg.loadImage(cafeMainImg)
        }

        val cafeLogoImg = cafe.logoUrl
        if (cafeLogoImg != null) {
            cafeLogo.isVisible = true
            cafeLogo.loadImage(cafeLogoImg)
        }

        cafeType.text = cafe.cafeType
        cafeName.text = cafe.name
        cafeDescription.text = cafe.description
        location.text = cafe.address
        time.text = cafe.workFrom.plus("-").plus(cafe.workTo)

        if (cafe.deliveryDiscount > 0) {
            takeawayDiscount.text =
                getString(R.string.takeaway_discount)
                    .format(cafe.deliveryDiscount.toString())

            takeawayDiscount.isVisible = true
        }

        deliveryFreeFrom.text = getString(R.string.delivery_free_from)
            .format(cafe.deliveryFreeFrom.toString())

        if (cafe.deliveryPrice != 0) {
            deliveryPrice.text =
                getString(R.string.delivery_price).format(cafe.deliveryPrice.toString())
            deliveryPrice.isVisible = true
        }

        if (cafe.businessFrom.isNotEmpty() && cafe.businessTo.isNotEmpty()) {
            timeSubtitle.isVisible = true
            businessTimeSubtitle.isVisible = true
            businessTime.isVisible = true
            businessTime.text = cafe.businessFrom.plus("-").plus(cafe.businessTo)
        }
    }
}
