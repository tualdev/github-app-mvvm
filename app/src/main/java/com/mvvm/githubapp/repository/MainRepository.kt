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

class MainRepository @Inject constructor(
        private val repoClient: RepoClient
) : Repository {

  @WorkerThread
  fun fetchRepositoryList(
    search: String,
    page: Int,
    onSuccess: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    var repos: List<Repo>

    if(search != "null"){
      val response = repoClient.fetchRepositoryList(search = search, page = page)
      response.suspendOnSuccess {
        data.whatIfNotNull { response ->
          repos = response.items
          emit(repos)
          onSuccess()
        }

      }.onError {
        map(ErrorResponseMapper) { onError("[Code: $error]: ${this.response}") }
      }.onException { onError(message) }
    }

  }.flowOn(Dispatchers.IO)
}
