package takeaway.app.activity

import takeaway.app.TakeawayView

interface MainActivityView : TakeawayView {

    fun showStatusBar()

    fun hideStatusBar()
}