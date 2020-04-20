package takeaway.di

import takeaway.feature.feed.data.api.TakeawayApi
import takeaway.feature.feed.data.datasource.CafeDataSource
import takeaway.feature.feed.data.datasource.CafeDataSourceImpl
import takeaway.feature.feed.data.repository.CafeRepositoryImpl
import takeaway.feature.feed.domain.repository.CafeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class DataModule {

    @AppScope
    @Binds
    abstract fun provideCafeRepository(repository: CafeRepositoryImpl): CafeRepository

    @AppScope
    @Binds
    abstract fun provideCafeDataSource(dataSource: CafeDataSourceImpl): CafeDataSource

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun providePokemonApi(retrofit: Retrofit): TakeawayApi =
            retrofit.create(TakeawayApi::class.java)
    }
}