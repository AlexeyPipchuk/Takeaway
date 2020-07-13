package takeaway.di.fragment

import dagger.Module
import dagger.Provides
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature.cafe.product.ui.ProductDialogFragment
import takeaway.feature.cafe.product.ui.product
import takeaway.feature.cafe.ui.cafe
import takeaway.shared_cafe.domain.entity.Cafe

@Module
object ProductDialogFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideProductArg(fragment: ProductDialogFragment): domain.entity.Product =
        fragment.args.product

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideCafeArg(fragment: ProductDialogFragment): Cafe =
        fragment.args.cafe
}