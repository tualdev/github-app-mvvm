

package com.mvvm.githubapp.extensions

import androidx.activity.ComponentActivity
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer

/** apply [TransformationLayout.Params] to an activity. */
fun ComponentActivity.onTransformationEndContainerApplyParams() {
  onTransformationEndContainer(intent.getParcelableExtra("com.skydoves.transformationlayout"))
}
