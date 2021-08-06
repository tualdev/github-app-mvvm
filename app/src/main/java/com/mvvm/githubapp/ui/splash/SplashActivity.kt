package com.mvvm.githubapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import com.mvvm.githubapp.R
import com.mvvm.githubapp.base.DataBindingActivity
import com.mvvm.githubapp.databinding.ActivitySplashBinding
import com.mvvm.githubapp.ui.main.MainActivity
import com.mvvm.githubapp.utils.NetworkUtils
import com.skydoves.transformationlayout.onTransformationStartContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : DataBindingActivity() {

  @VisibleForTesting val viewModel: SplashViewModel by viewModels()
  private val binding: ActivitySplashBinding by binding(R.layout.activity_splash)

  override fun onCreate(savedInstanceState: Bundle?) {
    onTransformationStartContainer()
    super.onCreate(savedInstanceState)
    binding.apply {
      lifecycleOwner = this@SplashActivity
      vm = viewModel
    }

    initView()
  }

  private fun initView() {

    if (NetworkUtils.isNetworkAvailable(applicationContext)){

      val background = object : Thread() {
        override fun run() {
          try {
            sleep(1000)
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
          }catch (e : Exception){
            e.printStackTrace()
          }
        }
      }
      background.start()
      return
    }

    openNoInternetDialog()

  }

  private fun openNoInternetDialog() {
    val alert = AlertDialog.Builder(this@SplashActivity)
    alert.setMessage(getString(R.string.there_is_no_internet))
    alert.setCancelable(false)
    alert.setPositiveButton(R.string.okay) { dialogInterface, i ->
      finish()
      dialogInterface.dismiss()
    }
    alert.show()
  }
}
