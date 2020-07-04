package com.adam.gnews.core.application.di

import android.content.Context
import com.adam.gnews.BuildConfig
import com.adam.gnews.utils.Constants.API_KEY
import com.adam.gnews.utils.Constants.CONTENT_TYPE_KEY
import com.adam.gnews.utils.Constants.CONTENT_TYPE_VALUE
import com.adam.gnews.utils.Constants.DATE_FORMAT
import com.adam.gnews.utils.Constants.NETWORK_REQUEST_TIMEOUT
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        @NetworkOkHttp okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun providesConnectionPool(): ConnectionPool = ConnectionPool()

    @Provides
    @NetworkOkHttp
    @Singleton
    fun providesOkHttpClient(
        connectionPool: ConnectionPool,
        cache: Cache
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .connectionPool(connectionPool)
        .connectTimeout(NETWORK_REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(NETWORK_REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(NETWORK_REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val originalRequest: Request = chain.request()

            val originalUrl = originalRequest.url

            val url = originalUrl.newBuilder()
                .addQueryParameter(API_KEY, BuildConfig.API_KEY)
                .build()

            val request: Request = originalRequest.newBuilder()
                .url(url)
                .header(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE)
                .method(originalRequest.method, originalRequest.body)
                .build()
            chain.proceed(request)
        }
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    @Provides
    @PicassoOkHttp
    @Singleton
    fun providesPicassoOkHttp(connectionPool: ConnectionPool, cache: Cache): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .connectionPool(connectionPool)
            .connectTimeout(NETWORK_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NETWORK_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

    @Provides
    @Singleton
    fun providesOkhttpCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder()
        .setDateFormat(DATE_FORMAT)
        .create()

    @Provides
    @Singleton
    fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun providesOkhttp3Downloader(@PicassoOkHttp okHttpClient: OkHttpClient): OkHttp3Downloader =
        OkHttp3Downloader(okHttpClient)

    @Provides
    @Singleton
    fun providesPicasso(context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso =
        Picasso.Builder(context)
            .downloader(okHttp3Downloader)
            .build()

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }
}