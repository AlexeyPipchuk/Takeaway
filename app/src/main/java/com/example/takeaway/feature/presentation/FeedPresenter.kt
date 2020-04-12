package com.example.takeaway.feature.presentation

import com.example.takeaway.app.BasePresenter
import com.example.takeaway.feature.ui.FeedView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FeedPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<FeedView>()