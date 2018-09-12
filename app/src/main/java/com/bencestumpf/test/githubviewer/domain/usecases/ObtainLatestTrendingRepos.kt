package com.bencestumpf.test.githubviewer.domain.usecases

import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.domain.providers.GitRepositoryProvider
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObtainLatestTrendingRepos @Inject constructor(private val gitRepositoryProvider: GitRepositoryProvider)
    : Usecase<List<GitRepository>> {

    private var page: Int = 1

    override fun getSubscribable(): Single<List<GitRepository>> {
        return gitRepositoryProvider.getTrendingRepos(7, page)
    }

    fun withParams(page: Int): ObtainLatestTrendingRepos {
        this.page = page
        return this
    }

}