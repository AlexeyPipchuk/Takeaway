package com.example.takeaway.di

import com.example.takeaway.app.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector

@AppScope
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    override fun inject(instance: App)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance app: App): AppComponent
    }
}