package com.bencestumpf.test.githubviewer.presentation.trending.di

import com.bencestumpf.test.githubviewer.di.scopes.ActivityScope
import com.bencestumpf.test.githubviewer.presentation.trending.TrendingActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent()
interface TrendingComponent {

    fun inject(activity: TrendingActivity)
}