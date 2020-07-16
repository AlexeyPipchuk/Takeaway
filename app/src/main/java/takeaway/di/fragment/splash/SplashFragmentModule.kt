package takeaway.di.fragment.splash

import dagger.Module
import dagger.Provides
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature_splash.ui.SplashFragment
import takeaway.feature_splash.ui.deepLink

@Module(includes = [SplashNavigationModule::class])
object SplashFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideDeepLinkArg(fragment: SplashFragment): String =
        fragment.args.deepLink.toString()
}