package com.bencestumpf.test.githubviewer.di.compoents

import android.app.Application
import com.bencestumpf.test.githubviewer.GithubViewerApplication
import com.bencestumpf.test.githubviewer.di.modules.ActivityBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivityBuilderModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: GithubViewerApplication)

}