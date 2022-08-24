package com.example.utvstories.data.remote.repository

import com.example.utvstories.data.remote.api.StoriesAPI
import com.example.utvstories.data.remote.models.ResponseDto
import com.example.utvstories.data.remote.models.StoryDto
import javax.inject.Inject

class StoriesRepository @Inject constructor(private val storiesAPI: StoriesAPI) {

    suspend fun loadStories(): List<StoryDto> = storiesAPI.getStoriesFromAPI().detail.stories
}