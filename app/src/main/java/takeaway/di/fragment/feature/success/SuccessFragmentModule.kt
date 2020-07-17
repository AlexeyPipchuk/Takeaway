package takeaway.di.fragment.feature.success

import dagger.Module
import dagger.Provides
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature_confirmation.ui.orderId
import takeaway.feature_success.ui.SuccessFragment

@Module(includes = [SuccessNavigationModule::class])
object SuccessFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideOrderIdArg(fragment: SuccessFragment): String =
        fragment.args.orderId
}