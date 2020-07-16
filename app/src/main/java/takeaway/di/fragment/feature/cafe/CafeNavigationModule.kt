package takeaway.di.fragment.feature.cafe

import dagger.Binds
import dagger.Module
import takeaway.app.navigation.feature.CafeRouterImpl
import takeaway.di.FragmentScope
import takeaway.feature_cafe.cafe.presentation.CafeRouter

@Module
interface CafeNavigationModule {

    @Binds
    @FragmentScope
    fun bindCafeRouter(router: CafeRouterImpl): CafeRouter
}