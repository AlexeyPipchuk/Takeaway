package takeaway.shared.nointernet.presentation

import base.TakeawayView

interface NoInternetView : TakeawayView {

    fun showProgress()

    fun hideProgress()

    fun closeApp()
}