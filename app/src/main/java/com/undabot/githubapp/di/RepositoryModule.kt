package com.undabot.githubapp.di

import com.undabot.githubapp.network.MovieClient
import com.undabot.githubapp.persistence.MovieDao
import com.undabot.githubapp.persistence.MovieInfoDao
import com.undabot.githubapp.repository.DetailRepository
import com.undabot.githubapp.repository.MainRepository
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
          movieClient: MovieClient,
          movieDao: MovieDao
  ): MainRepository {
    return MainRepository(movieClient)
  }

  @Provides
  @ViewModelScoped
  fun provideDetailRepository(
          movieClient: MovieClient,
          movieInfoDao: MovieInfoDao
  ): DetailRepository {
    return DetailRepository(movieClient, movieInfoDao)
  }
}
