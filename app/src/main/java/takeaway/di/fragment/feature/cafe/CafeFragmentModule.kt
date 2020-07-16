package takeaway.di.fragment.feature.cafe

import dagger.Module
import dagger.Provides
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature_cafe.cafe.ui.CafeFragment
import takeaway.feature_cafe.cafe.ui.cafe
import takeaway.shared_cafe.domain.entity.Cafe

@Module(includes = [CafeNavigationModule::class])
object CafeFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideCafeArg(fragment: CafeFragment): Cafe =
        fragment.args.cafe
}