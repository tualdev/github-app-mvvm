package com.mvvm.githubapp.model

import com.squareup.moshi.Json

data class ApiErrorResponse(
        @field:Json(name = "Error") val error: String,
        @field:Json(name = "Response") val response: String?
)
