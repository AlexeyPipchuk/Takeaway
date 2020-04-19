package com.example.takeaway.feature.feed.presentation

import com.example.takeaway.app.BasePresenter
import com.example.takeaway.app.navigation.Screen
import com.example.takeaway.feature.feed.domain.usecase.GetCafeListUseCase
import com.example.takeaway.feature.feed.domain.entity.Cafe
import com.example.takeaway.feature.feed.ui.FeedView
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FeedPresenter @Inject constructor(
    private val getCafeListUseCase: GetCafeListUseCase,
    private val router: Router
) : BasePresenter<FeedView>() {

    private var cafeListCache: List<Cafe>? = null

    override fun onViewAttach() {
        super.onViewAttach()

        getCafeListUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cafeList ->
                    cafeListCache = cafeList
                    val cafeItemList = cafeList.map(::mapCafeToCafeItem)
                    view?.setFeed(cafeItemList)
                },
                { error ->

                }
            )
            .addToDisposable()
    }

    private fun mapCafeToCafeItem(cafe: Cafe): CafeItem =
        CafeItem(
            cafeName = cafe.name,
            deliveryDiscount = cafe.deliveryDiscount,
            deliveryFreeFrom = cafe.minDeliverySum,
            imageUrl = cafe.imgUrls?.firstOrNull(),
            logoUrl = cafe.logoUrl
        )

    fun onInfoButtonClicked() {
        router.navigateTo(Screen.InfoScreen)
    }
}