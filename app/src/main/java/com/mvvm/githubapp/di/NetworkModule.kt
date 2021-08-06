package com.mvvm.githubapp.di

import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.mvvm.githubapp.constant.AppConstant
import com.mvvm.githubapp.network.HttpRequestInterceptor
import com.mvvm.githubapp.network.RepoClient
import com.mvvm.githubapp.network.RepoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(HttpRequestInterceptor())
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(AppConstant.GITHUB_API_BASE_URL)
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
      .build()
  }

  @Provides
  @Singleton
  fun providePokedexService(retrofit: Retrofit): RepoService {
    return retrofit.create(RepoService::class.java)
  }

  @Provides
  @Singleton
  fun providePokedexClient(repoService: RepoService): RepoClient {
    return RepoClient(repoService)
  }
}
