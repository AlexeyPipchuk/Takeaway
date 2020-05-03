package takeaway.feature.basket.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import kotlinx.android.synthetic.main.basket_appbar.view.*
import kotlinx.android.synthetic.main.basket_fragment.*
import takeaway.app.BaseFragment
import takeaway.app.loadImage
import takeaway.feature.basket.presentation.BasketPresenter
import takeaway.feature.feed.domain.entity.Cafe
import javax.inject.Inject

class BasketFragment : BaseFragment(R.layout.basket_fragment), BasketView {

    companion object {
        fun getInstance(): Fragment = BasketFragment()
    }

    @Inject
    lateinit var presenter: BasketPresenter

    //private var adapter: BasketAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
    }

    private fun initListeners() {
        basketAppbar.backToCafeButton.setOnClickListener {
            presenter.onBackButtonClick()
        }
    }

    override fun showOrderCafeInfo(cafe: Cafe) {
        orderCafeName.text = cafe.name

        val cafeLogoImg = cafe.logoUrl
        if (cafeLogoImg != null) {
            orderCafeLogo.isVisible = true
            orderCafeLogoImg.loadImage(cafeLogoImg)
        }
    }

    override fun showEmptyContent() {
        basketContent.isVisible = false
        emptyBasketText.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}