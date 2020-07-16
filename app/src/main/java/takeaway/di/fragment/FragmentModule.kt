package takeaway.di.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import takeaway.di.FragmentScope
import takeaway.di.fragment.feature.AddCafeNavigationModule
import takeaway.di.fragment.feature.BasketNavigationModule
import takeaway.di.fragment.feature.InfoNavigationModule
import takeaway.di.fragment.feature.confirmation.ConfirmationFragmentModule
import takeaway.di.fragment.feature.feed.FeedFragmentModule
import takeaway.di.fragment.feature.orderregistration.OrderRegistrationFragmentModule
import takeaway.di.fragment.feature.success.SuccessFragmentModule
import takeaway.di.fragment.shared.NoInternetNavigationModule
import takeaway.di.fragment.shared.PrivacyPolicyNavigationModule
import takeaway.di.fragment.feature.splash.SplashFragmentModule
import takeaway.feature.cafe.product.severalcafe.SeveralCafeWarningDialogFragment
import takeaway.feature.cafe.product.ui.ProductDialogFragment
import takeaway.feature.cafe.ui.CafeFragment
import takeaway.feature_order_registration.ui.OrderRegistrationFragment
import takeaway.feature_add_cafe.ui.AddCafeFragment
import takeaway.feature_basket.ui.BasketFragment
import takeaway.feature_confirmation.ui.ConfirmationFragment
import takeaway.feature_feed.feed.ui.FeedFragment
import takeaway.feature_feed.promo.ui.PromoDialogFragment
import takeaway.feature_info.ui.InfoFragment
import takeaway.feature_splash.ui.SplashFragment
import takeaway.feature_success.ui.SuccessFragment
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
    @ContributesAndroidInjector(modules = [InfoNavigationModule::class])
    fun provideInfoFragment(): InfoFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CafeFragmentModule::class])
    fun provideCafeFragment(): CafeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ProductDialogFragmentModule::class])
    fun provideProductDialogFragment(): ProductDialogFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [BasketNavigationModule::class])
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
    @ContributesAndroidInjector(modules = [AddCafeNavigationModule::class])
    fun provideAddCafeFragment(): AddCafeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [NoInternetNavigationModule::class])
    fun provideNoInternetFragment(): NoInternetFragment
}