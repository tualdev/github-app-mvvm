package com.undabot.githubapp.network

import com.undabot.githubapp.model.MovieInfo
import com.undabot.githubapp.model.MovieResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

  @GET("/")
  suspend fun fetchMovieList(
    @Query("s") search: String,
    @Query("apiKey") apiKey: String,
  ): ApiResponse<MovieResponse>

  @GET("/")
  suspend fun fetchMovieInfo(
          @Query("i") imdbID: String,
          @Query("apiKey") apiKey: String): ApiResponse<MovieInfo>
}
