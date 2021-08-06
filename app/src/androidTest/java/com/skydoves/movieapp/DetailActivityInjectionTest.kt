

package com.undabot.githubapp

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.undabot.githubapp.ui.details.DetailActivity
import com.undabot.githubapp.utils.MockUtil
import com.undabot.transformationlayout.TransformationLayout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DetailActivityInjectionTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @Test
  fun verifyInjection() {
    val intent = Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java)
    val transformationLayout = TransformationLayout(ApplicationProvider.getApplicationContext())
    transformationLayout.transitionName = "lapras"
    intent.putExtra("com.undabot.transformationlayout", transformationLayout.getParcelableParams())
    intent.putExtra(DetailActivity.MOVIE, MockUtil.mockPokemon())
    ActivityScenario.launch<DetailActivity>(intent).use {
      it.moveToState(Lifecycle.State.CREATED)
      it.onActivity { activity ->
        assertThat(activity.viewModel).isNotNull()
        activity.viewModel.movieInfoLiveData.observe(activity) { pokemonList ->
          assertThat(pokemonList).isNotNull()
        }
      }
    }
  }
}
