package com.example.takeaway.feature.feed.domain.repository

import com.example.takeaway.feature.feed.domain.entity.Cafe
import io.reactivex.Single

interface CafeRepository {

    fun getList(): Single<List<Cafe>>
}