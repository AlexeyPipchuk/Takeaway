package takeaway.shared_error.presentation

import base.TakeawayView

interface NoInternetView : TakeawayView {

    fun showProgress()

    fun hideProgress()

    fun closeApp()
}