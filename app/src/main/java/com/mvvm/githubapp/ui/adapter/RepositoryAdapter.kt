package com.mvvm.githubapp.ui.adapter

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.mvvm.githubapp.R
import com.mvvm.githubapp.databinding.ItemRepositoryBinding
import com.mvvm.githubapp.model.Repo
import com.mvvm.githubapp.ui.details.DetailActivity

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

  private val items: MutableList<Repo> = mutableListOf()
  private var onClickedAt = 0L

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ItemRepositoryBinding>(inflater, R.layout.item_repository, parent, false)

    return RepositoryViewHolder(binding).apply {
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

  fun setRepositoryList(repoList: List<Repo>) {
    items.clear()
    items.addAll(repoList)
    notifyDataSetChanged()
  }

  override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
    holder.binding.apply {
      repository = items[position]
      executePendingBindings()
    }
  }

  override fun getItemCount() = items.size

  class RepositoryViewHolder(val binding: ItemRepositoryBinding) :
    RecyclerView.ViewHolder(binding.root)
}
