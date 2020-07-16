package takeaway.di.fragment.feature.success

import dagger.Module
import dagger.Provides
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature_confirmation.ui.orderId

@Module(includes = [SuccessNavigationModule::class])
object SuccessFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideOrderIdArg(fragment: takeaway.feature_success.ui.SuccessFragment): String =
        fragment.args.orderId
}