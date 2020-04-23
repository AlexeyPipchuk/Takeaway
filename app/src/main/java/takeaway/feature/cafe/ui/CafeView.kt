package takeaway.feature.cafe.ui

import takeaway.app.TakeawayView

interface CafeView : TakeawayView {

    fun setCafeName(name: String)

    fun showProgress()

    fun hideProgress()

    fun showNoInternetDialog()

    fun showServiceUnavailable()
}