package com.undabot.githubapp.repository

import androidx.annotation.WorkerThread
import com.undabot.githubapp.mapper.ErrorResponseMapper
import com.undabot.githubapp.model.Movie
import com.undabot.githubapp.network.MovieClient
import com.skydoves.sandwich.map
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(
        private val movieClient: MovieClient
) : Repository {

  @WorkerThread
  fun fetchMovieList(
    search: String,
    onSuccess: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    var movies: List<Movie>

    if(search != "null"){
      val response = movieClient.fetchMovieList(search = search)
      response.suspendOnSuccess {
        data.whatIfNotNull { response ->
          movies = response.search
          emit(movies)
          onSuccess()
        }

      }.onError {
        map(ErrorResponseMapper) { onError("[Code: $error]: ${this.response}") }
      }.onException { onError(message) }
    }

  }.flowOn(Dispatchers.IO)
}
