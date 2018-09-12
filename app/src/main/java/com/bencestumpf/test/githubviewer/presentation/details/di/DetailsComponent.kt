package com.bencestumpf.test.githubviewer.presentation.details.di

import com.bencestumpf.test.githubviewer.di.scopes.ActivityScope
import com.bencestumpf.test.githubviewer.presentation.details.DetailsActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent()
interface DetailsComponent {

    fun inject(activity: DetailsActivity)
}