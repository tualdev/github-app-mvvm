package com.undabot.githubapp.network

import com.undabot.githubapp.model.MovieInfo
import com.undabot.githubapp.model.MovieResponse
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class MovieClient @Inject constructor(
  private val movieService: MovieService
) {

  suspend fun fetchMovieList(
    search: String
  ): ApiResponse<MovieResponse> =
    movieService.fetchMovieList(
      search = search,
            apiKey = "fcf9059"
    )

  suspend fun fetchMovieInfo(
          imdbID: String,
  ): ApiResponse<MovieInfo> =
    movieService.fetchMovieInfo(
      imdbID = imdbID,
            apiKey = "fcf9059"
    )
}
