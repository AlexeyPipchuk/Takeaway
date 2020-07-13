package takeaway.di.fragment.shared

import dagger.Binds
import dagger.Module
import takeaway.app.navigation.shared.PrivacyPolicyRouterImpl
import takeaway.di.FragmentScope
import takeaway.shared_privacy_policy.presentation.PrivacyPolicyRouter

@Module
interface PrivacyPolicyNavigationModule {

    @Binds
    @FragmentScope
    fun bindPrivacyPolicyRouter(router: PrivacyPolicyRouterImpl): PrivacyPolicyRouter
}