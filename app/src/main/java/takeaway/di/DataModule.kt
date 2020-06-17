package takeaway.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import data.repository.PhoneCountryRepositoryImpl
import domain.repository.PhoneCountryRepository
import retrofit2.Retrofit
import takeaway.feature.feed.data.api.CafeApi
import takeaway.feature.feed.data.repository.CafeRepositoryImpl
import takeaway.feature.feed.domain.repository.CafeRepository
import takeaway.feature.order.registration.data.network.OrderApi
import takeaway.feature.order.registration.data.repository.CreateOrderRepositoryImpl
import takeaway.feature.order.registration.domain.repository.CreateOrderRepository
import takeaway.shared.basket.data.repository.BasketRepositoryImpl
import takeaway.shared.basket.domian.repository.BasketRepository
import takeaway.shared.cafe.data.api.ProductApi
import takeaway.shared.cafe.data.repository.ProductRepositoryImpl
import takeaway.shared.cafe.domain.repository.ProductRepository
import takeaway.shared.category.data.api.CategoryApi
import takeaway.shared.category.data.repository.CategoryRepositoryImpl
import takeaway.shared.category.domain.repository.CategoryRepository

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
    abstract fun provideCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository

    @AppScope
    @Binds
    abstract fun provideBasketRepository(repository: BasketRepositoryImpl): BasketRepository

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
        fun provideCategoryApi(retrofit: Retrofit): CategoryApi =
            retrofit.create(CategoryApi::class.java)

        @JvmStatic
        @Provides
        fun provideCreateOrderApi(retrofit: Retrofit): OrderApi =
            retrofit.create(OrderApi::class.java)
    }
}