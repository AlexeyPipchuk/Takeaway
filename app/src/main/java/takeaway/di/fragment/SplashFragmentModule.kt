package takeaway.di.fragment

import dagger.Module
import dagger.Provides
import takeaway.app.args
import takeaway.di.FragmentScope
import takeaway.feature.splash.ui.SplashFragment
import takeaway.feature.splash.ui.deepLink

@Module
object SplashFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideDeepLinkArg(fragment: SplashFragment): String =
        fragment.args.deepLink.toString()
}