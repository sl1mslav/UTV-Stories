package com.example.utvstories.data.remote.models

import com.example.utvstories.entity.Story
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseDto (
    @Json(name="detail") val detail: DetailDto
)

@JsonClass(generateAdapter = true)
data class DetailDto (
    @Json(name = "stories") val stories: List<StoryDto>
)

@JsonClass(generateAdapter = true)
data class StoryDto (
    @Json(name="image_logo") override val preview: String,
    @Json(name="news_name") override val title: String,
    @Json(name="url") override val url: String,
    @Json(name = "date") val date: Long,
    override val postDate: String = ""
): Story