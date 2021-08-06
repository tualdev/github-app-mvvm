package com.mvvm.githubapp.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.map
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import com.mvvm.githubapp.mapper.ErrorResponseMapper
import com.mvvm.githubapp.model.Repo
import com.mvvm.githubapp.network.RepoClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepository @Inject constructor(
        private val repoClient: RepoClient
) : Repository {

  @WorkerThread
  fun fetchRepoInfo(
          owner: String,
          repo: String,
          onSuccess: () -> Unit,
          onError: (String?) -> Unit
  ) = flow<Repo?> {
    val response = repoClient.fetchRepoInfo(owner = owner, repo = repo)
    response.suspendOnSuccess {
      data.whatIfNotNull { response ->
        emit(response)
        onSuccess()
      }
    }.onError {
      map(ErrorResponseMapper) { onError("[Code: $error]: ${this.response}") }
    }
    // handles the case when the API request gets an exception response.
    // e.g., network connection error.
    .onException { onError(message) }
  }.flowOn(Dispatchers.IO)
}
