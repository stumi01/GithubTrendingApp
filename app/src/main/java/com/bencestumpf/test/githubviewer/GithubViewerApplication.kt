package com.bencestumpf.test.githubviewer

import android.app.Application
import com.bencestumpf.test.githubviewer.di.Injector
import com.bencestumpf.test.githubviewer.helper.OpenClass

@OpenClass
class GithubViewerApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initializeInjection()
    }

    private fun initializeInjection() {
        Injector.initialize(this)
    }

}