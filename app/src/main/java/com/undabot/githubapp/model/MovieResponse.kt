package com.undabot.githubapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
        @field:Json(name = "Search") val search: List<Movie>,
        @field:Json(name = "totalResults") val totalResults: String?,
        @field:Json(name = "Response") val response: String?
)
