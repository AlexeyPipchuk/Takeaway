package takeaway.di.fragment.feature

import dagger.Binds
import dagger.Module
import takeaway.app.navigation.feature.InfoRouterImpl
import takeaway.di.FragmentScope
import takeaway.feature_info.presentation.InfoRouter

@Module
interface InfoNavigationModule {

    @Binds
    @FragmentScope
    fun bindInfoRouter(router: InfoRouterImpl): InfoRouter
}