package takeaway.di.fragment.shared

import dagger.Binds
import dagger.Module
import takeaway.app.navigation.shared.NoInternetRouterImpl
import takeaway.di.FragmentScope
import takeaway.shared_error.presentation.NoInternetRouter

@Module
interface NoInternetNavigationModule {

    @Binds
    @FragmentScope
    fun bindNoInternetRouter(router: NoInternetRouterImpl): NoInternetRouter
}