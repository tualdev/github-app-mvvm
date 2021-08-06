package com.undabot.githubapp.di

import android.app.Application
import androidx.room.Room
import com.undabot.githubapp.persistence.AppDatabase
import com.undabot.githubapp.persistence.MovieDao
import com.undabot.githubapp.persistence.MovieInfoDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

  @Provides
  @Singleton
  fun provideMoshi(): Moshi {
    return Moshi.Builder().build()
  }

  @Provides
  @Singleton
  fun provideAppDatabase(
    application: Application,
  ): AppDatabase {
    return Room
      .databaseBuilder(application, AppDatabase::class.java, "movie.db")
      .fallbackToDestructiveMigration()
      .build()
  }

  @Provides
  @Singleton
  fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
    return appDatabase.movieDao()
  }

  @Provides
  @Singleton
  fun provideMovieInfoDao(appDatabase: AppDatabase): MovieInfoDao {
    return appDatabase.movieInfoDao()
  }
}
