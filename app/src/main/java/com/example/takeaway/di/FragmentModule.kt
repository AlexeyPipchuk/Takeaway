package com.example.takeaway.di

import com.example.takeaway.feature.ui.FeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun provideFeedFragment(): FeedFragment
}