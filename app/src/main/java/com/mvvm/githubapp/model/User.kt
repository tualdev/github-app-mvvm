package com.mvvm.githubapp.model

import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity
@JsonClass(generateAdapter = true)
data class User (
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "login") val login: String,
    @field:Json(name = "avatar_url") val avatarUrl: String,
    @field:Json(name = "html_url") val htmlUrl: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "followers_url") val followers: String,
    @field:Json(name = "following_url") val following: String,
): Serializable
