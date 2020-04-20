package takeaway.di

import com.example.takeaway.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

@Module
class NetworkModule {

    @AppScope
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy.MM.dd")
        .create()

    @AppScope
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            builder.addInterceptor(httpLoggingInterceptor)
        }

        return builder.build()
    }

    @AppScope
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor {
            Timber.tag("okhttp").d(it)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @AppScope
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://oleksey-oleksandrovich.ru:1337/")
            .build()
}