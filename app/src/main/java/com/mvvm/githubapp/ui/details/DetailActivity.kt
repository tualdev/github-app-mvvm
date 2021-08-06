

package com.mvvm.githubapp.ui.details

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.mvvm.githubapp.BuildConfig
import com.mvvm.githubapp.R
import com.mvvm.githubapp.base.DataBindingActivity
import com.mvvm.githubapp.databinding.ActivityDetailBinding
import com.mvvm.githubapp.extensions.onTransformationEndContainerApplyParams
import com.mvvm.githubapp.model.Repo
import com.mvvm.githubapp.utils.CommonUtils
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
    DetailViewModel.provideFactory(detailViewModelFactory, repoItem.owner!!.login, repoItem.name)
  }

  private val binding: ActivityDetailBinding by binding(R.layout.activity_detail)
  private val repoItem: Repo by bundleNonNull(REPO)

  override fun onCreate(savedInstanceState: Bundle?) {
    onTransformationEndContainerApplyParams()
    super.onCreate(savedInstanceState)
    binding.apply {
      lifecycleOwner = this@DetailActivity
      repo = repoItem
      vm = viewModel
    }

    binding.openUserDetailsTv.setOnClickListener {
      CommonUtils.launchUrl(this@DetailActivity, viewModel.repoInfoLiveData.value!!.owner!!.htmlUrl)
    }

    binding.openRepoDetailsTv.setOnClickListener {
      CommonUtils.launchUrl(this@DetailActivity, viewModel.repoInfoLiveData.value!!.htmlUrl)
    }
  }

  companion object {
    @VisibleForTesting
    const val REPO = "REPO"

    fun startActivity(transformationLayout: TransformationLayout, repo: Repo) =
      transformationLayout.context.intentOf<DetailActivity> {

        if (BuildConfig.FLAVOR == "free"){
          Toast.makeText(transformationLayout.context, "it is free :(", Toast.LENGTH_SHORT).show()
        }else{
          putExtra(REPO to repo)
          TransformationCompat.startActivity(transformationLayout, intent)
        }
      }
  }
}
