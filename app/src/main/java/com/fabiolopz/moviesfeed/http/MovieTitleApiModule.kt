package com.fabiolopz.moviesfeed.http

import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class MovieTitleApiModule {

    @Provides
    fun provideClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                var request: Request = chain.request()
                val url: HttpUrl = request
                    .url.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }.build()
    }

    @Provides
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(): MoviesApiService {
        return provideRetrofit(BASE_URL, provideClient()).create(MoviesApiService::class.java)
    }

    companion object {
        const val BASE_URL: String = "https://api.themoviedb.org/3/movie/"
        const val API_KEY: String = "bc0182d9a7b591eb8b97964a74b469ee"
    }
}