package takeaway.di.fragment.feature.feed

import dagger.Module
import dagger.Provides
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature_feed.feed.ui.FeedFragment
import takeaway.feature_feed.feed.ui.noInternet

@Module(includes = [FeedNavigationModule::class])
object FeedFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideNoInternetArg(fragment: FeedFragment): Boolean =
        fragment.args.noInternet
}