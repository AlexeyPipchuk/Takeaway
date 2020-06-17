package takeaway.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import takeaway.app.App
import takeaway.app.DeepLinkDefiner
import takeaway.app.ErrorConverter
import takeaway.app.activity.MainActivity
import takeaway.app.navigation.DeepLinkValidator
import takeaway.di.fragment.FragmentModule

@Module(
    includes = [
        AndroidInjectionModule::class,
        NavigationModule::class,
        NetworkModule::class,
        DataModule::class
    ]
)
abstract class AppModule {

    @Module
    companion object {

        @JvmStatic
        @AppScope
        @Provides
        fun provideContext(app: App): Context =
            app.applicationContext

        @JvmStatic
        @AppScope
        @Provides
        fun provideDeepLinkDefiner(): DeepLinkDefiner = DeepLinkDefiner()

        @JvmStatic
        @AppScope
        @Provides
        fun provideErrorConverter(): ErrorConverter = ErrorConverter()

        @JvmStatic
        @AppScope
        @Provides
        fun provideDeepLinkValidator(app: App): DeepLinkValidator =
            DeepLinkValidator(app.applicationContext)
    }

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            FragmentModule::class
        ]
    )
    abstract fun provideMainActivity(): MainActivity
}
