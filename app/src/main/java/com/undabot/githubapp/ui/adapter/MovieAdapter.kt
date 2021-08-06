package com.undabot.githubapp.ui.adapter

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.undabot.githubapp.R
import com.undabot.githubapp.databinding.ItemMovieBinding
import com.undabot.githubapp.model.Movie
import com.undabot.githubapp.ui.details.DetailActivity

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

  private val items: MutableList<Movie> = mutableListOf()
  private var onClickedAt = 0L

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ItemMovieBinding>(inflater, R.layout.item_movie, parent, false)

    return MovieViewHolder(binding).apply {
      binding.root.setOnClickListener {
        val position = bindingAdapterPosition.takeIf { it != NO_POSITION }
          ?: return@setOnClickListener
        val currentClickedAt = SystemClock.elapsedRealtime()
        if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
          DetailActivity.startActivity(binding.transformationLayout, items[position])
          onClickedAt = currentClickedAt
        }
      }
    }
  }

  fun setMovieList(movieList: List<Movie>) {
    items.clear()
    items.addAll(movieList)
    notifyDataSetChanged()
  }

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    holder.binding.apply {
      movie = items[position]
      executePendingBindings()
    }
  }

  override fun getItemCount() = items.size

  class MovieViewHolder(val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root)
}
