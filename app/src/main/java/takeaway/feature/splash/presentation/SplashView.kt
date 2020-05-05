package takeaway.feature.splash.presentation

import takeaway.app.TakeawayView

interface SplashView : TakeawayView {

    fun showStatusBar()

    fun hideStatusBar()
}