package com.bencestumpf.test.githubviewer.domain.providers

import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import io.reactivex.Maybe
import io.reactivex.Single

//Well, in this case the providers should be called repositories but the GitRepositoryRepository is very confusing.
class GitRepositoryProvider(private val remote: Remote, private val cache: Cache) {
    fun getTrendingRepos(): Single<List<GitRepository>> {
        return remote.getTrending(7)
                .doOnSuccess {
                    cache.add(it)
                }
    }

    fun getRepository(id: String): Single<GitRepository> {
        return cache.getRepository(id)
                .switchIfEmpty(remote.getRepository(id))
    }

    interface Remote {
        fun getTrending(rangeInDays: Int): Single<List<GitRepository>>

        fun getRepository(id: String): Single<GitRepository>
    }

    interface Cache {
        fun getRepository(id: String): Maybe<GitRepository>

        fun add(repositories: List<GitRepository>)
    }

}