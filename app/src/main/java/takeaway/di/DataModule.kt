package takeaway.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import data.repository.PhoneCountryRepositoryImpl
import domain.repository.PhoneCountryRepository
import retrofit2.Retrofit
import takeaway.feature_order_registration.data.network.OrderApi
import takeaway.feature_order_registration.data.repository.CreateOrderRepositoryImpl
import takeaway.feature_order_registration.domain.repository.CreateOrderRepository
import takeaway.shared.cafe.data.api.ProductApi
import takeaway.shared.cafe.data.repository.ProductRepositoryImpl
import takeaway.shared.cafe.domain.repository.ProductRepository
import takeaway.shared_category.data.api.CategoryApi
import takeaway.shared_category.data.repository.CategoryRepositoryImpl
import takeaway.shared_category.domain.repository.CategoryRepository
import takeaway.shared_cafe.data.api.CafeApi
import takeaway.shared_cafe.data.repository.CafeRepositoryImpl
import takeaway.shared_cafe.domain.repository.CafeRepository

@Module
abstract class DataModule {

    // Repositories

    @AppScope
    @Binds
    abstract fun provideCafeRepository(repository: CafeRepositoryImpl): CafeRepository

    @AppScope
    @Binds
    abstract fun provideProductRepository(repository: ProductRepositoryImpl): ProductRepository

    @AppScope
    @Binds
    abstract fun provideCategoryRepository(repository: takeaway.shared_category.data.repository.CategoryRepositoryImpl): takeaway.shared_category.domain.repository.CategoryRepository

    @AppScope
    @Binds
    abstract fun provideBasketRepository(repository: takeaway.shared_basket.data.repository.BasketRepositoryImpl): takeaway.shared_basket.domain.repository.BasketRepository

    @AppScope
    @Binds
    abstract fun providePhoneCountryRepository(repository: PhoneCountryRepositoryImpl): PhoneCountryRepository

    @AppScope
    @Binds
    abstract fun provideCreateOrderRepository(repository: CreateOrderRepositoryImpl): CreateOrderRepository

    // API

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideCafeApi(retrofit: Retrofit): CafeApi =
            retrofit.create(CafeApi::class.java)

        @JvmStatic
        @Provides
        fun provideProductApi(retrofit: Retrofit): ProductApi =
            retrofit.create(ProductApi::class.java)

        @JvmStatic
        @Provides
        fun provideCategoryApi(retrofit: Retrofit): takeaway.shared_category.data.api.CategoryApi =
            retrofit.create(takeaway.shared_category.data.api.CategoryApi::class.java)

        @JvmStatic
        @Provides
        fun provideCreateOrderApi(retrofit: Retrofit): OrderApi =
            retrofit.create(OrderApi::class.java)
    }
}