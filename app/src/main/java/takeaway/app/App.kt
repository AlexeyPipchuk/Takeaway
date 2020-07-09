package takeaway.app

import com.example.takeaway.BuildConfig
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import takeaway.di.DaggerAppComponent

const val YANDEX_METRICA_API_KEY = "8b206c8b-678f-49c0-953f-b653aac2f194"

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent
            .factory()
            .create(this)

    override fun onCreate() {
        super.onCreate()

        if (!BuildConfig.DEBUG) {
            // Creating an extended library configuration.
            val config = YandexMetricaConfig.newConfigBuilder(YANDEX_METRICA_API_KEY).build()
            // Initializing the AppMetrica SDK.
            YandexMetrica.activate(applicationContext, config)
            // Automatic tracking of user activity.
            YandexMetrica.enableActivityAutoTracking(this)
        }
    }
}