package com.undabot.githubapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity
@JsonClass(generateAdapter = true)
data class Movie(
        @field:Json(name = "Title") @PrimaryKey val title: String,
        @field:Json(name = "Year") val year: String,
        @field:Json(name = "imdbID") val imdbID: String,
        @field:Json(name = "Type") val type: String,
        @field:Json(name = "Poster") val poster: String,
) : Serializable
