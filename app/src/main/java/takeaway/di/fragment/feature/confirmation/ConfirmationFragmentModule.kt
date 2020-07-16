package takeaway.di.fragment.feature.confirmation

import dagger.Module
import dagger.Provides
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature_confirmation.ui.ConfirmationFragment
import takeaway.feature_confirmation.ui.orderId

@Module(includes = [ConfirmationNavigationModule::class])
object ConfirmationFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideOrderIdArg(fragment: ConfirmationFragment): String =
        fragment.args.orderId
}