package takeaway.di.fragment

import dagger.Module
import dagger.Provides
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature.confirmation.ui.orderId
import takeaway.feature.success.ui.SuccessFragment

@Module
object SuccessFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideOrderIdArg(fragment: SuccessFragment): String =
        fragment.args.orderId
}