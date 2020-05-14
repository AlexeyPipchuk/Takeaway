package takeaway.di.fragment

import dagger.Module
import dagger.Provides
import takeaway.app.args
import takeaway.di.FragmentScope
import takeaway.feature.order.registration.ui.OrderRegistrationFragment
import takeaway.feature.order.registration.ui.orderSketch
import takeaway.shared.order.registration.domain.entity.OrderSketch

@Module
object OrderRegistrationFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideOrderRegistrationArg(fragment: OrderRegistrationFragment): OrderSketch =
        fragment.args.orderSketch
}