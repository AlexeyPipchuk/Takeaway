package takeaway.di

import takeaway.app.activity.MainActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [
    AndroidInjectionModule::class,
    NavigationModule::class,
    NetworkModule::class,
    DataModule::class
])
interface AppModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        FragmentModule::class
    ])
    fun provideMainActivity(): MainActivity
}
