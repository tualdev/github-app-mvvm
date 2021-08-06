package com.undabot.githubapp.ui.main

import androidx.annotation.MainThread
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.undabot.githubapp.base.LiveCoroutinesViewModel
import com.undabot.githubapp.model.Movie
import com.undabot.githubapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val mainRepository: MainRepository
) : LiveCoroutinesViewModel() {

  private val movieFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
  val movieListLiveData: LiveData<List<Movie>>

  private val toastLiveData: MutableLiveData<String> = MutableLiveData()
  private val searchValueLiveData: MutableLiveData<String> = MutableLiveData()
  private val searchValue: LiveData<String> get() = searchValueLiveData
  val toastValue: LiveData<String> get() = toastLiveData

  val isLoading: ObservableBoolean = ObservableBoolean(false)

  init {
    Timber.d("init MainViewModel")

    movieListLiveData = movieFetchingIndex.asLiveData().switchMap { page ->
      isLoading.set(true)
      mainRepository.fetchMovieList(
        search = searchValue.value.toString(),
        onSuccess = { isLoading.set(false) },
        onError = { toastLiveData.postValue(it) }
      ).asLiveDataOnViewModelScope()
    }
  }

  fun fetchMovieList(search: String){
    searchValueLiveData.value = search
    fetchMovieList()
  }

  @MainThread
  fun fetchMovieList() = movieFetchingIndex.value++
}
