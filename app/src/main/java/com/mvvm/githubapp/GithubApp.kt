package com.mvvm.githubapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlin.properties.Delegates

@HiltAndroidApp
class GithubApp : Application(){

    companion object {
        var instance: GithubApp by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
