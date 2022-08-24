package com.example.utvstories.data.remote.api

import com.example.utvstories.data.remote.models.ResponseDto
import retrofit2.http.GET

interface StoriesAPI {
    @GET("stories")
    suspend fun getStoriesFromAPI() : ResponseDto
}