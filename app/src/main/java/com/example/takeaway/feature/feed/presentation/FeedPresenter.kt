package com.example.takeaway.feature.feed.presentation

import com.example.takeaway.app.BasePresenter
import com.example.takeaway.app.navigation.Screen
import com.example.takeaway.feature.feed.model.CafeItem
import com.example.takeaway.feature.feed.ui.FeedView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FeedPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<FeedView>() {

    override fun onViewCreated() {
        super.onViewCreated()

        val list = listOf(
            CafeItem(
                "Torta",
                "соточку заплоти",
                "скидочка хуидочка"
            ),
            CafeItem(
                "Torta",
                "соточку заплоти",
                "скидочка хуидочка"
            ),
            CafeItem(
                "Torta",
                "соточку заплоти",
                "скидочка хуидочка"
            ),
            CafeItem(
                "Torta",
                "соточку заплоти",
                "скидочка хуидочка"
            )
        )
        view?.setFeed(list)
    }

    fun onInfoButtonClicked() {
        router.navigateTo(Screen.InfoScreen)
    }
}