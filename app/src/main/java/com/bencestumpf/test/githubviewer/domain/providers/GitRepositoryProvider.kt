package com.bencestumpf.test.githubviewer.domain.providers

import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import io.reactivex.Single

//Well, in this case the providers should be called repositories but the GitRepositoryRepository is very confusing.
class GitRepositoryProvider(private val remote: Remote) {
    fun getTrendingRepos(): Single<List<GitRepository>> {
        return remote.getTrending(7)
    }


    interface Remote {
        fun getTrending(rangeInDays: Int): Single<List<GitRepository>>
    }
}