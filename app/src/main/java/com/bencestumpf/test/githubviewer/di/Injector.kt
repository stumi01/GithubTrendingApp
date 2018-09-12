package com.bencestumpf.test.githubviewer.di

import android.support.annotation.VisibleForTesting
import com.bencestumpf.test.githubviewer.GithubViewerApplication
import com.bencestumpf.test.githubviewer.di.compoents.AppComponent
import com.bencestumpf.test.githubviewer.di.compoents.DaggerAppComponent
import com.bencestumpf.test.githubviewer.di.modules.AppModule

enum class Injector() {
    INSTANCE;

    lateinit var applicationComponent: AppComponent

    companion object {

        internal fun initialize(app: GithubViewerApplication) {
            val applicationComponent = DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()
            INSTANCE.applicationComponent = applicationComponent
        }

        fun getAppComponent(): AppComponent {
            return INSTANCE.applicationComponent
        }

        @VisibleForTesting
        fun setComponent(appComponent: AppComponent) {
            INSTANCE.applicationComponent = appComponent
        }
    }
}