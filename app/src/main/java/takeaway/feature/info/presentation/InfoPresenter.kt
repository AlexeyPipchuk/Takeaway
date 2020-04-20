package com.example.takeaway.feature.info.presentation

import com.example.takeaway.app.BasePresenter
import com.example.takeaway.feature.info.ui.InfoView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class InfoPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<InfoView>() {

    fun onBackClicked() {
        router.backTo(null)
    }
}