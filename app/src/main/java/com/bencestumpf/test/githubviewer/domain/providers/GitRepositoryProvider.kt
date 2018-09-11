package com.bencestumpf.test.githubviewer.domain.providers

import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

//Well, in this case the providers should be called repositories but the GitRepositoryRepository is very confusing.
@Singleton
class GitRepositoryProvider @Inject constructor() {
    fun getTrendingRepos(): Single<List<GitRepository>> {
        return Single.just(arrayListOf(GitRepository("First repo"), GitRepository("Second repo")))
    }
}