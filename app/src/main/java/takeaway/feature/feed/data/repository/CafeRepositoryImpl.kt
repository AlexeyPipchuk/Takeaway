package com.example.takeaway.feature.feed.data.repository

import com.example.takeaway.feature.feed.data.converter.CafeConverter
import com.example.takeaway.feature.feed.data.datasource.CafeDataSource
import com.example.takeaway.feature.feed.data.model.CafeListResponse
import com.example.takeaway.feature.feed.domain.entity.Cafe
import com.example.takeaway.feature.feed.domain.repository.CafeRepository
import io.reactivex.Single
import javax.inject.Inject

class CafeRepositoryImpl @Inject constructor(
    private val dataSource: CafeDataSource,
    private val converter: CafeConverter
) : CafeRepository {

    override fun getList(): Single<List<Cafe>> =
        dataSource.getList()
            .map(converter::toCafeList)
}