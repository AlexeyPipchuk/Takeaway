package takeaway.di.fragment

import dagger.Module
import dagger.Provides
import takeaway.app.args
import takeaway.di.FragmentScope
import takeaway.feature.cafe.domain.entity.Product
import takeaway.feature.cafe.ui.cafe
import takeaway.feature.cafe.ui.product.ProductDialogFragment
import takeaway.feature.cafe.ui.product.product
import takeaway.feature.feed.domain.entity.Cafe

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