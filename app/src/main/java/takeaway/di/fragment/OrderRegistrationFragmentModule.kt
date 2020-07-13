package takeaway.di.fragment

import dagger.Module
import dagger.Provides
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature.order.registration.ui.OrderRegistrationFragment
import takeaway.feature.order.registration.ui.orderSketch

@Module
object OrderRegistrationFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideOrderRegistrationArg(fragment: OrderRegistrationFragment): domain.entity.OrderSketch =
        fragment.args.orderSketch
}