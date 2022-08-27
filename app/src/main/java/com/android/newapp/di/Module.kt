package com.android.newapp.di

import android.content.Context
import androidx.room.Room
import com.android.newapp.data.local.MyRoomDatabase
import com.android.newapp.data.network.WebServices
import com.android.newapp.helpers.Const
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
        //  .retryOnConnectionFailure(false)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {

                    val originalRequest = chain.request()
                    val originalUrl = originalRequest.url
                    val url = originalUrl.newBuilder()
                        .addQueryParameter("api_key" , Const.API_KEY)
                        .build()
                    val requestBuilder = originalRequest.newBuilder().url(url)
                    //  .addHeader("Connection", "close")

                    val request = requestBuilder.build()

                    val response = chain.proceed(request)

                    response.code//status code
                    return response
                }
            })
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun getWebService(retrofit: Retrofit): WebServices {

        return retrofit.create(WebServices::class.java)
    }

    @Provides
    @Singleton
    fun getRoomIns (@ApplicationContext context : Context) : MyRoomDatabase {

        return Room.databaseBuilder(context,MyRoomDatabase::class.java,"DB")
            .fallbackToDestructiveMigration()
            .build()
    }
}