package takeaway.shared.nointernet.presentation

import takeaway.app.TakeawayView

interface NoInternetView : TakeawayView {

    fun showProgress()

    fun hideProgress()

    fun closeApp()
}