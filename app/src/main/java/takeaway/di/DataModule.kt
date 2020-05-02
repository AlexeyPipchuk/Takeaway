package takeaway.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import takeaway.feature.cafe.data.api.ProductApi
import takeaway.feature.cafe.data.datasource.ProductDataSource
import takeaway.feature.cafe.data.datasource.ProductDataSourceImpl
import takeaway.feature.cafe.data.repository.ProductRepositoryImpl
import takeaway.feature.cafe.domain.repository.ProductRepository
import takeaway.feature.feed.data.api.CafeApi
import takeaway.feature.feed.data.datasource.CafeDataSource
import takeaway.feature.feed.data.datasource.CafeDataSourceImpl
import takeaway.feature.feed.data.repository.CafeRepositoryImpl
import takeaway.feature.feed.domain.repository.CafeRepository
import takeaway.shared.basket.data.datasource.BasketDataSource
import takeaway.shared.basket.data.datasource.BasketDataSourceImpl
import takeaway.shared.basket.data.repository.BasketRepositoryImpl
import takeaway.shared.basket.domian.repository.BasketRepository

@Module
abstract class DataModule {

    @AppScope
    @Binds
    abstract fun provideCafeRepository(repository: CafeRepositoryImpl): CafeRepository

    @AppScope
    @Binds
    abstract fun provideCafeDataSource(dataSource: CafeDataSourceImpl): CafeDataSource

    @AppScope
    @Binds
    abstract fun provideProductRepository(repository: ProductRepositoryImpl): ProductRepository

    @AppScope
    @Binds
    abstract fun provideProductDataSource(dataSource: ProductDataSourceImpl): ProductDataSource

    @AppScope
    @Binds
    abstract fun provideBasketRepository(repository: BasketRepositoryImpl): BasketRepository

    @AppScope
    @Binds
    abstract fun provideBasketDataSource(dataSource: BasketDataSourceImpl): BasketDataSource

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
    }
}