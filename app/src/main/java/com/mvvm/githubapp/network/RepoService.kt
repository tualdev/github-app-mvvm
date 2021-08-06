package com.mvvm.githubapp.network

import com.skydoves.sandwich.ApiResponse
import com.mvvm.githubapp.model.Repo
import com.mvvm.githubapp.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoService {

  @GET("search/repositories")
  suspend fun searchRepos(
          @Query(value = "q", encoded = true) query: String,
          @Query("sort") sort: String,
          @Query("order") order: String,
          @Query("page") page: Int,
          @Query("per_page") perPage: Int,
  ): ApiResponse<SearchResult>

  @GET("repos/{owner}/{repo}")
  suspend fun fetchRepoInfo(
          @Path("owner") owner: String,
          @Path("repo") repo: String
  ): ApiResponse<Repo>
}
