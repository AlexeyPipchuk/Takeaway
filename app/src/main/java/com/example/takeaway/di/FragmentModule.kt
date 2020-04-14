package com.example.takeaway.di

import com.example.takeaway.feature.feed.ui.FeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun provideFeedFragment(): FeedFragment
}