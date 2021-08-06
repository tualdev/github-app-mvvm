package com.mvvm.githubapp.network

import com.skydoves.sandwich.ApiResponse
import com.mvvm.githubapp.constant.AppConstant
import com.mvvm.githubapp.model.Repo
import com.mvvm.githubapp.model.SearchResult
import javax.inject.Inject

class RepoClient @Inject constructor(
  private val repoService: RepoService
) {

  suspend fun fetchRepositoryList(
          search: String,
          sort: String,
          page: Int
  ): ApiResponse<SearchResult> =
          repoService.searchRepos(
                  query = search,
                  sort = sort,
                  order = "desc",
                  page = page,
                  perPage = AppConstant.PAGE_SIZE
          )

  suspend fun fetchRepoInfo(
          owner: String,
          repo: String,
  ): ApiResponse<Repo> =
    repoService.fetchRepoInfo(
            owner = owner,
            repo = repo
    )
}
