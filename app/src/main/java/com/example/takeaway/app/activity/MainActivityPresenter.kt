package com.example.takeaway.app.activity

import com.example.takeaway.app.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<MainActivityView>()