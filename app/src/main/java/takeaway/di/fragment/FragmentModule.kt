package takeaway.di.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import takeaway.di.FragmentScope
import takeaway.feature.basket.ui.BasketFragment
import takeaway.feature.cafe.product.severalcafe.SeveralCafeWarningDialogFragment
import takeaway.feature.cafe.product.ui.ProductDialogFragment
import takeaway.feature.cafe.ui.CafeFragment
import takeaway.feature.feed.promo.ui.PromoDialogFragment
import takeaway.feature.feed.ui.FeedFragment
import takeaway.feature.info.ui.InfoFragment
import takeaway.feature.order.registration.ui.OrderRegistrationFragment
import takeaway.feature.splash.ui.SplashFragment
import takeaway.shared.privacy.policy.ui.PrivacyPolicyFragment

@Module
interface FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun provideSplashFragment(): SplashFragment

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

    @FragmentScope
    @ContributesAndroidInjector
    fun providePromoDialogFragment(): PromoDialogFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun provideSeveralCafeWarningDialogFragment(): SeveralCafeWarningDialogFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun providePrivacyPolicyFragment(): PrivacyPolicyFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [OrderRegistrationFragmentModule::class])
    fun provideOrderRegistrationFragment(): OrderRegistrationFragment
}