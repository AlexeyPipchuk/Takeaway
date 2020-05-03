package takeaway.di.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import takeaway.di.FragmentScope
import takeaway.feature.basket.ui.BasketFragment
import takeaway.feature.cafe.ui.CafeFragment
import takeaway.feature.cafe.ui.product.ProductDialogFragment
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

    @FragmentScope
    @ContributesAndroidInjector(modules = [ProductDialogFragmentModule::class])
    fun provideProductDialogFragment(): ProductDialogFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun provideBasketFragment(): BasketFragment
}