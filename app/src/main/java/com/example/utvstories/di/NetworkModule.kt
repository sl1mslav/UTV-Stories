package com.example.utvstories.di

import com.example.utvstories.data.remote.api.StoriesAPI
import com.example.utvstories.util.Constants.STORIES_API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideBaseURL(): String = STORIES_API_URL

    @Provides
    fun provideRetrofitClient(baseUrl: String) : Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    fun provideStoriesAPIService(retrofit: Retrofit): StoriesAPI {
        return retrofit.create(StoriesAPI::class.java)
    }

}