package takeaway.di.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import takeaway.di.FragmentScope
import takeaway.di.fragment.shared.NoInternetNavigationModule
import takeaway.di.fragment.shared.PrivacyPolicyNavigationModule
import takeaway.feature.addcafe.ui.AddCafeFragment
import takeaway.feature.basket.ui.BasketFragment
import takeaway.feature.cafe.product.severalcafe.SeveralCafeWarningDialogFragment
import takeaway.feature.cafe.product.ui.ProductDialogFragment
import takeaway.feature.cafe.ui.CafeFragment
import takeaway.feature.confirmation.ui.ConfirmationFragment
import takeaway.feature.feed.promo.ui.PromoDialogFragment
import takeaway.feature.feed.ui.FeedFragment
import takeaway.feature.info.ui.InfoFragment
import takeaway.feature.order.registration.ui.OrderRegistrationFragment
import takeaway.feature.splash.ui.SplashFragment
import takeaway.feature.success.ui.SuccessFragment
import takeaway.shared_error.ui.NoInternetFragment
import takeaway.shared_privacy_policy.ui.PrivacyPolicyFragment

@Module
interface FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SplashFragmentModule::class])
    fun provideSplashFragment(): SplashFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FeedFragmentModule::class])
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
    @ContributesAndroidInjector(modules = [PrivacyPolicyNavigationModule::class])
    fun providePrivacyPolicyFragment(): PrivacyPolicyFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [OrderRegistrationFragmentModule::class])
    fun provideOrderRegistrationFragment(): OrderRegistrationFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ConfirmationFragmentModule::class])
    fun provideConfirmationFragment(): ConfirmationFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SuccessFragmentModule::class])
    fun provideSuccessFragment(): SuccessFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun provideAddCafeFragment(): AddCafeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [NoInternetNavigationModule::class])
    fun provideNoInternetFragment(): NoInternetFragment
}