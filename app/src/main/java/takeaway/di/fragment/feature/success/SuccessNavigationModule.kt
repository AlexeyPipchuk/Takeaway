package takeaway.di.fragment.feature.success

import dagger.Binds
import dagger.Module
import takeaway.app.navigation.feature.SuccessRouterImpl
import takeaway.di.FragmentScope
import takeaway.feature_success.presentation.SuccessRouter

@Module
interface SuccessNavigationModule {

    @Binds
    @FragmentScope
    fun bindSuccessRouter(router: SuccessRouterImpl): SuccessRouter
}