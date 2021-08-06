package com.undabot.githubapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class MovieInfo(
  @field:Json(name = "Title") @PrimaryKey val title: String,
  @field:Json(name = "Year") val year: String,
  @field:Json(name = "Rated") val rated: String,
  @field:Json(name = "Released") val released: String,
  @field:Json(name = "Runtime") val runtime: String,
  @field:Json(name = "Genre") val genre: String,
  @field:Json(name = "Director") val director: String,
  @field:Json(name = "Writer") val writer: String,
  @field:Json(name = "Actors") val actors: String,
  @field:Json(name = "Plot") val plot: String,
  @field:Json(name = "Language") val language: String,
  @field:Json(name = "Country") val country: String,
  @field:Json(name = "Awards") val awards: String,
  @field:Json(name = "Poster") val poster: String,
  @field:Json(name = "Metascore") val metascore: String,
  @field:Json(name = "imdbRating") val imdbRating: String,
  @field:Json(name = "imdbID") val imdbID: String,
  @field:Json(name = "Type") val type: String
)
