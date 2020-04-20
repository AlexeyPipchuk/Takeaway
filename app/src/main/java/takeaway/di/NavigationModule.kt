package com.example.takeaway.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
class NavigationModule {

    @AppScope
    @Provides
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @AppScope
    @Provides
    fun provideNavigationHolder(cicerone: Cicerone<Router>): NavigatorHolder =
        cicerone.navigatorHolder

    @AppScope
    @Provides
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router
}