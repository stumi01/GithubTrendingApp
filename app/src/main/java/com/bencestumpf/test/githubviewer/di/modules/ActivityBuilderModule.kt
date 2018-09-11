package com.bencestumpf.test.githubviewer.di.modules

import com.bencestumpf.test.githubviewer.di.scopes.ActivityScope
import com.bencestumpf.test.githubviewer.presentation.trending.TrendingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    internal abstract fun bindTrendingActivity(): TrendingActivity

}
