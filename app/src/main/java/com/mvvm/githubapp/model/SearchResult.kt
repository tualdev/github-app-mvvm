package com.mvvm.githubapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SearchResult(
    @field:Json(name = "total_count") val totalCount: Int,
    @field:Json(name = "incomplete_results") val incompleteResults: Boolean = false,
    @field:Json(name = "items") val items: List<Repo>,
)
