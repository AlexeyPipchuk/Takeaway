package takeaway.di.fragment

import dagger.Module
import dagger.Provides
import takeaway.app.args
import takeaway.di.FragmentScope
import takeaway.feature.feed.ui.FeedFragment
import takeaway.feature.feed.ui.noInternet

@Module
object FeedFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideNoInternetArg(fragment: FeedFragment): Boolean =
        fragment.args.noInternet
}