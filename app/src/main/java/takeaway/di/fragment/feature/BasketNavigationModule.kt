package takeaway.di.fragment.feature

import dagger.Binds
import dagger.Module
import takeaway.app.navigation.feature.BasketRouterImpl
import takeaway.di.FragmentScope
import takeaway.feature_basket.presentation.BasketRouter

@Module
interface BasketNavigationModule {

    @Binds
    @FragmentScope
    fun bindBasketRouter(router: BasketRouterImpl): BasketRouter
}