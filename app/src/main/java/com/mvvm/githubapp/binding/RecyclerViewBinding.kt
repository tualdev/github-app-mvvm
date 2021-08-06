package com.mvvm.githubapp.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.whatif.whatIfNotNullAs
import com.skydoves.whatif.whatIfNotNullOrEmpty
import com.mvvm.githubapp.model.Repo
import com.mvvm.githubapp.ui.adapter.RepositoryAdapter
import com.mvvm.githubapp.ui.main.MainViewModel

object RecyclerViewBinding {

  @JvmStatic
  @BindingAdapter("adapter")
  fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter.apply {
      stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
  }

  @JvmStatic
  @BindingAdapter("paginationRepoList")
  fun paginationRepoList(view: RecyclerView, viewModel: MainViewModel) {
    RecyclerViewPaginator(
      recyclerView = view,
      isLoading = { viewModel.isLoading.get() },
      loadMore = { viewModel.fetchRepoList() },
      onLast = { false }
    ).run {
      threshold = 8
    }
  }

  @JvmStatic
  @BindingAdapter("adapterRepositoryList")
  fun bindAdapterRepositoryList(view: RecyclerView, repoList: List<Repo>?) {
    repoList.whatIfNotNullOrEmpty { itemList ->
      view.adapter.whatIfNotNullAs<RepositoryAdapter> { adapter ->
        adapter.setRepositoryList(itemList)
      }
    }
  }
}
