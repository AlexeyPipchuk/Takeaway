package com.example.takeaway.di

import com.example.takeaway.app.activity.MainActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [
    AndroidInjectionModule::class,
    NavigationModule::class
])
interface AppModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        FragmentModule::class
    ])
    fun provideMainActivity(): MainActivity
}
