package takeaway.app

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import takeaway.di.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent
            .factory()
            .create(this)
}