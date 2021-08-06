package com.undabot.githubapp.ui.details

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.undabot.githubapp.base.LiveCoroutinesViewModel
import com.undabot.githubapp.model.MovieInfo
import com.undabot.githubapp.repository.DetailRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

class DetailViewModel @AssistedInject constructor(
  detailRepository: DetailRepository,
  @Assisted private val imdbID: String
) : LiveCoroutinesViewModel() {

  val movieInfoLiveData: LiveData<MovieInfo?>

  private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
  val toastLiveData: LiveData<String> get() = _toastLiveData

  val isLoading: ObservableBoolean = ObservableBoolean(true)

  init {
    Timber.d("init DetailViewModel")

    movieInfoLiveData = detailRepository.fetchMovieInfo(
      imdbId = imdbID,
      onSuccess = { isLoading.set(false) },
      onError = { _toastLiveData.postValue(it) }
    ).asLiveDataOnViewModelScope()
  }

  @dagger.assisted.AssistedFactory
  interface AssistedFactory {
    fun create(imdbID: String): DetailViewModel
  }

  companion object {
    fun provideFactory(
            assistedFactory: AssistedFactory,
            imdbID: String
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return assistedFactory.create(imdbID) as T
      }
    }
  }
}
