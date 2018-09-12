package com.bencestumpf.test.githubviewer.di.compoents

import com.bencestumpf.test.githubviewer.di.modules.AppModule
import com.bencestumpf.test.githubviewer.presentation.details.di.DetailsComponent
import com.bencestumpf.test.githubviewer.presentation.trending.di.TrendingComponent
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun trendingComponent(): TrendingComponent


    fun detailsComponent(): DetailsComponent

}