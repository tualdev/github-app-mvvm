package com.undabot.githubapp.repository

import androidx.annotation.WorkerThread
import com.undabot.githubapp.mapper.ErrorResponseMapper
import com.undabot.githubapp.model.MovieInfo
import com.undabot.githubapp.network.MovieClient
import com.undabot.githubapp.persistence.MovieInfoDao
import com.skydoves.sandwich.map
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepository @Inject constructor(
        private val movieClient: MovieClient,
        private val movieInfoDao: MovieInfoDao
) : Repository {

  @WorkerThread
  fun fetchMovieInfo(
          imdbId: String,
          onSuccess: () -> Unit,
          onError: (String?) -> Unit
  ) = flow<MovieInfo?> {
    val movieInfo = movieInfoDao.getMovieInfo(imdbId)
    if (movieInfo == null) {

      val response = movieClient.fetchMovieInfo(imdbID = imdbId)
      response.suspendOnSuccess {
        data.whatIfNotNull { response ->
          movieInfoDao.insertMovieInfo(response)
          emit(response)
          onSuccess()
        }
      }
        // handles the case when the API request gets an error response.
        // e.g., internal server error.
        .onError {
          map(ErrorResponseMapper) { onError("[Code: $error]: ${this.response}") }
        }
        // handles the case when the API request gets an exception response.
        // e.g., network connection error.
        .onException { onError(message) }
    } else {
      emit(movieInfo)
      onSuccess()
    }
  }.flowOn(Dispatchers.IO)
}
