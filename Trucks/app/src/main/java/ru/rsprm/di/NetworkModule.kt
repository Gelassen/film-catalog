package ru.rsprm.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.rsprm.network.Api
import ru.rsprm.network.ConnectionInterceptor
import ru.rsprm.network.NetworkUtils
import javax.inject.Named
import javax.inject.Singleton


const val NETWORK = "NETWORK"
const val UI = "UI"

@Module
class NetworkModule(val context: Context, val url: String) {

    @Singleton
    @Named(NETWORK)
    @Provides
    fun provideNetworkScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Singleton
    @Named(UI)
    @Provides
    fun provideUIScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Singleton
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(networkUtils: NetworkUtils): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient
            .Builder()
            .addInterceptor(ConnectionInterceptor(context, networkUtils))
            .addInterceptor(logging)
            .build()

        return httpClient
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: OkHttpClient): Api {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(url)
            .build()

        return retrofit.create(Api::class.java)
    }
}