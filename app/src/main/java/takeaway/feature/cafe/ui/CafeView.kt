package takeaway.feature.cafe.ui

import takeaway.app.TakeawayView
import takeaway.feature.feed.domain.entity.Cafe

interface CafeView : TakeawayView {

    fun showCafeInfo(cafe: Cafe)

    fun showProgress()

    fun hideProgress()

    fun showNoInternetDialog()

    fun showServiceUnavailable()
}