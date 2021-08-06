package com.mvvm.githubapp.ui.details

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvm.githubapp.base.LiveCoroutinesViewModel
import com.mvvm.githubapp.model.Repo
import com.mvvm.githubapp.repository.DetailRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

class DetailViewModel @AssistedInject constructor(
  detailRepository: DetailRepository,
  @Assisted private val owner: String,
  @Assisted private val repo: String,
) : LiveCoroutinesViewModel() {

  val repoInfoLiveData: LiveData<Repo?>

  private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
  val toastLiveData: LiveData<String> get() = _toastLiveData

  val isLoading: ObservableBoolean = ObservableBoolean(true)

  init {
    Timber.d("init DetailViewModel")

    repoInfoLiveData = detailRepository.fetchRepoInfo(
      owner = owner,
      repo = repo,
      onSuccess = { isLoading.set(false) },
      onError = { _toastLiveData.postValue(it) }
    ).asLiveDataOnViewModelScope()
  }

  @dagger.assisted.AssistedFactory
  interface AssistedFactory {
    fun create(owner: String, repo: String): DetailViewModel
  }

  companion object {
    fun provideFactory(
            assistedFactory: AssistedFactory,
            owner: String,
            repo: String
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return assistedFactory.create(owner, repo) as T
      }
    }
  }
}
