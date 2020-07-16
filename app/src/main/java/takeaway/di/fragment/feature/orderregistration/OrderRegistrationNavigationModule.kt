package takeaway.di.fragment.feature.orderregistration

import dagger.Binds
import dagger.Module
import takeaway.app.navigation.feature.OrderRegistrationRouterImpl
import takeaway.di.FragmentScope
import takeaway.feature_order_registration.presentation.OrderRegistrationRouter

@Module
interface OrderRegistrationNavigationModule {

    @Binds
    @FragmentScope
    fun bindOrderRegistrationRouter(router: OrderRegistrationRouterImpl): OrderRegistrationRouter
}