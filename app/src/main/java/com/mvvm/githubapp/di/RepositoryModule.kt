package com.mvvm.githubapp.di

import com.mvvm.githubapp.network.RepoClient
import com.mvvm.githubapp.repository.DetailRepository
import com.mvvm.githubapp.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideMainRepository(
          repoClient: RepoClient
  ): MainRepository {
    return MainRepository(repoClient)
  }

  @Provides
  @ViewModelScoped
  fun provideDetailRepository(
          repoClient: RepoClient
  ): DetailRepository {
    return DetailRepository(repoClient)
  }
}
