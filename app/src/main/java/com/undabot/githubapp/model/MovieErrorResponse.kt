package com.undabot.githubapp.model

import com.squareup.moshi.Json

/**
 * A customized movie error response.
 *
 * @param error A network response code.
 * @param response A network error message.
 */
data class MovieErrorResponse(
        @field:Json(name = "Error") val error: String,
        @field:Json(name = "Response") val response: String?
)
