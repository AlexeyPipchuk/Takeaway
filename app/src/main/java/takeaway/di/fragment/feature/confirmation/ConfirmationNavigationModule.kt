package takeaway.di.fragment.feature.confirmation

import dagger.Binds
import dagger.Module
import takeaway.app.navigation.feature.ConfirmationRouterImpl
import takeaway.di.FragmentScope
import takeaway.feature_confirmation.presentation.ConfirmationRouter

@Module
interface ConfirmationNavigationModule {

    @Binds
    @FragmentScope
    fun bindConfirmationRouter(router: ConfirmationRouterImpl): ConfirmationRouter
}