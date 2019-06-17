package ru.rsprm.network

import android.content.Context
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.rsprm.model.Truck

interface Api {

    @get:GET("/test/trucks")
    val trucks: Flowable<List<Truck>>

    @POST("/test/truck/add")
    fun create(@Body truck: Truck): Observable<Truck>

    @PATCH("/test/truck/{id}")
    fun edit(@Body truck: Truck, @Path("id") id: Int): Observable<Truck>

    @DELETE("/test/truck/{id}")
    fun remove(@Path("id") id: Int): Observable<Long>

    @Deprecated("Provided as DI dependency")
    companion object Factory {
        fun create(context: Context): Api {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val httpClient = OkHttpClient
                .Builder()
                .addInterceptor(ConnectionInterceptor(context, /*networkUtils*/ NetworkUtils()))
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .baseUrl("http://rsprm.ru/test/")
                .build()

            return retrofit.create(Api::class.java)
        }
    }

}
