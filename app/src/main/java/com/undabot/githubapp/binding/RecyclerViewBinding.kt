package com.undabot.githubapp.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.undabot.githubapp.model.Movie
import com.undabot.githubapp.ui.adapter.MovieAdapter
import com.undabot.githubapp.ui.main.MainViewModel
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.whatif.whatIfNotNullAs
import com.skydoves.whatif.whatIfNotNullOrEmpty

object RecyclerViewBinding {

  @JvmStatic
  @BindingAdapter("adapter")
  fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter.apply {
      stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
  }

  @JvmStatic
  @BindingAdapter("paginationMovieList")
  fun paginationMovieList(view: RecyclerView, viewModel: MainViewModel) {
    RecyclerViewPaginator(
      recyclerView = view,
      isLoading = { viewModel.isLoading.get() },
      loadMore = { viewModel.fetchMovieList() },
      onLast = { false }
    ).run {
      threshold = 8
    }
  }

  @JvmStatic
  @BindingAdapter("adapterMovieList")
  fun bindAdapterMovieList(view: RecyclerView, movieList: List<Movie>?) {
    movieList.whatIfNotNullOrEmpty { itemList ->
      view.adapter.whatIfNotNullAs<MovieAdapter> { adapter ->
        adapter.setMovieList(itemList)
      }
    }
  }
}
