package takeaway.di.fragment.feature.feed

import dagger.Binds
import dagger.Module
import takeaway.app.navigation.feature.FeedRouterImpl
import takeaway.di.FragmentScope
import takeaway.feature_feed.feed.presentation.FeedRouter

@Module
interface FeedNavigationModule {

    @Binds
    @FragmentScope
    fun bindFeedRouter(router: FeedRouterImpl): FeedRouter
}