

package com.undabot.githubapp.ui.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.undabot.githubapp.R
import com.undabot.githubapp.base.DataBindingActivity
import com.undabot.githubapp.databinding.ActivityDetailBinding
import com.undabot.githubapp.extensions.onTransformationEndContainerApplyParams
import com.undabot.githubapp.model.Movie
import com.skydoves.bundler.bundleNonNull
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : DataBindingActivity() {

  @Inject
  lateinit var detailViewModelFactory: DetailViewModel.AssistedFactory

  @VisibleForTesting
  val viewModel: DetailViewModel by viewModels {
    DetailViewModel.provideFactory(detailViewModelFactory, movieItem.imdbID)
  }

  private val binding: ActivityDetailBinding by binding(R.layout.activity_detail)
  private val movieItem: Movie by bundleNonNull(MOVIE)

  override fun onCreate(savedInstanceState: Bundle?) {
    onTransformationEndContainerApplyParams()
    super.onCreate(savedInstanceState)
    binding.apply {
      lifecycleOwner = this@DetailActivity
      movie = movieItem
      vm = viewModel
    }
  }

  companion object {
    @VisibleForTesting
    const val MOVIE = "MOVIE"

    fun startActivity(transformationLayout: TransformationLayout, movie: Movie) =
      transformationLayout.context.intentOf<DetailActivity> {
        putExtra(MOVIE to movie)
        TransformationCompat.startActivity(transformationLayout, intent)
      }
  }
}
