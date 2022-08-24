package com.example.utvstories.domain

import com.example.utvstories.data.remote.repository.StoriesRepository
import com.example.utvstories.entity.Story
import com.example.utvstories.util.TimeFormatter.convertToDate
import javax.inject.Inject

class GetStoriesWithDateUseCase @Inject constructor(
    private val repository: StoriesRepository
) {
    suspend fun execute(): List<Story> = repository.loadStories().map {
        it.copy(
            postDate = convertToDate(it.date)
        )
    }
}