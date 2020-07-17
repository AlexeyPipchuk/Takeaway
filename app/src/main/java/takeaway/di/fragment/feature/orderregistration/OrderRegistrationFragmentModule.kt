package takeaway.di.fragment.feature.orderregistration

import dagger.Module
import dagger.Provides
import domain.entity.OrderSketch
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature_order_registration.ui.OrderRegistrationFragment
import takeaway.feature_order_registration.ui.orderSketch

@Module(includes = [OrderRegistrationNavigationModule::class])
object OrderRegistrationFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideOrderRegistrationArg(fragment: OrderRegistrationFragment): OrderSketch =
        fragment.args.orderSketch
}