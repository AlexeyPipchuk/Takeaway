package takeaway.di.fragment

import dagger.Module
import dagger.Provides
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature.confirmation.ui.ConfirmationFragment
import takeaway.feature.confirmation.ui.orderId

@Module
object ConfirmationFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideOrderIdArg(fragment: ConfirmationFragment): String =
        fragment.args.orderId
}