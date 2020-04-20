package com.example.takeaway.di

import com.example.takeaway.feature.feed.data.api.TakeawayApi
import com.example.takeaway.feature.feed.data.datasource.CafeDataSource
import com.example.takeaway.feature.feed.data.datasource.CafeDataSourceImpl
import com.example.takeaway.feature.feed.data.repository.CafeRepositoryImpl
import com.example.takeaway.feature.feed.domain.repository.CafeRepository
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