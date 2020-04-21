package takeaway.di.fragment

import dagger.Module
import dagger.Provides
import takeaway.app.args
import takeaway.di.FragmentScope
import takeaway.feature.cafe.ui.CafeFragment
import takeaway.feature.cafe.ui.cafe
import takeaway.feature.feed.domain.entity.Cafe

@Module
object CafeFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideCafeArg(fragment: CafeFragment): Cafe =
        fragment.args.cafe
}