package com.mvvm.githubapp.ui.main

import androidx.annotation.MainThread
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.mvvm.githubapp.base.LiveCoroutinesViewModel
import com.mvvm.githubapp.model.Repo
import com.mvvm.githubapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val mainRepository: MainRepository
) : LiveCoroutinesViewModel() {

  private val repoFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
  val repoListLiveData: LiveData<List<Repo>>

  private val toastLiveData: MutableLiveData<String> = MutableLiveData()

  private val searchValueLiveData: MutableLiveData<String> = MutableLiveData()
  private val sortValueLiveData: MutableLiveData<String> = MutableLiveData()

  private val searchValue: LiveData<String> get() = searchValueLiveData
  private val sortValue: LiveData<String> get() = sortValueLiveData

  val toastValue: LiveData<String> get() = toastLiveData

  val isLoading: ObservableBoolean = ObservableBoolean(false)

  init {
    Timber.d("init MainViewModel")

    repoListLiveData = repoFetchingIndex.asLiveData().switchMap { page ->
      mainRepository.fetchRepositoryList(
        search = searchValue.value.toString(), sort = sortValue.value.toString(), page = page,
        onSuccess = { isLoading.set(false) },
        onError = { toastLiveData.postValue(it) }
      ).asLiveDataOnViewModelScope()
    }
  }

  fun fetchRepoList(search: String, sort: String, page: Int){
    isLoading.set(true)
    searchValueLiveData.value = search
    sortValueLiveData.value = sort
    repoFetchingIndex.value = page
    fetchRepoList()
  }

  @MainThread
  fun fetchRepoList() = repoFetchingIndex.value++
}
