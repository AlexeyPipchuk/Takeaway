package takeaway.di.fragment.feature.splash

import dagger.Binds
import dagger.Module
import takeaway.app.navigation.feature.SplashRouterImpl
import takeaway.di.FragmentScope
import takeaway.feature_splash.presentation.SplashRouter

@Module
interface SplashNavigationModule {

    @Binds
    @FragmentScope
    fun bindSplashRouter(router: SplashRouterImpl): SplashRouter
}