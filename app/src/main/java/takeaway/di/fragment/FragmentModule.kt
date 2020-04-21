package takeaway.di.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import takeaway.di.FragmentScope
import takeaway.feature.cafe.ui.CafeFragment
import takeaway.feature.feed.ui.FeedFragment
import takeaway.feature.info.ui.InfoFragment

@Module
interface FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun provideFeedFragment(): FeedFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun provideInfoFragment(): InfoFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CafeFragmentModule::class])
    fun provideCafeFragment(): CafeFragment
}