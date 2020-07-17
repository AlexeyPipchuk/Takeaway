package takeaway.di.fragment.feature.cafe

import dagger.Module
import dagger.Provides
import domain.entity.Product
import extensions.args
import takeaway.di.FragmentScope
import takeaway.feature_cafe.cafe.ui.cafe
import takeaway.feature_cafe.product.ui.ProductDialogFragment
import takeaway.feature_cafe.product.ui.product
import takeaway.shared_cafe.domain.entity.Cafe

@Module
object ProductDialogFragmentModule {

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideProductArg(fragment: ProductDialogFragment): Product =
        fragment.args.product

    @Provides
    @FragmentScope
    @JvmStatic
    fun provideCafeArg(fragment: ProductDialogFragment): Cafe =
        fragment.args.cafe
}