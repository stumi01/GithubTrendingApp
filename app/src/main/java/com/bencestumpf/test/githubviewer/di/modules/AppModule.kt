package com.bencestumpf.test.githubviewer.di.modules

import com.bencestumpf.test.githubviewer.GithubViewerApplication
import com.bencestumpf.test.githubviewer.helper.OpenClass
import dagger.Module

@OpenClass
@Module(includes = [RetrofitModule::class, ProviderModule::class])
class AppModule(val app: GithubViewerApplication) {

}
