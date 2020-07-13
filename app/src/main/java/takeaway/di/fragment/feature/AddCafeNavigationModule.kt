package takeaway.di.fragment.feature

import dagger.Binds
import dagger.Module
import takeaway.app.navigation.feature.AddCafeRouterImpl
import takeaway.di.FragmentScope
import takeaway.feature_add_cafe.presentation.AddCafeRouter

@Module
interface AddCafeNavigationModule {

    @Binds
    @FragmentScope
    fun bindAddCafeRouter(router: AddCafeRouterImpl): AddCafeRouter
}