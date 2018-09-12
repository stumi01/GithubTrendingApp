package com.bencestumpf.test.githubviewer.domain.providers

import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import io.reactivex.Maybe
import io.reactivex.Single
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

//Well, in this case the providers should be called repositories but the GitRepositoryRepository is very confusing.
class GitRepositoryProvider(private val remote: Remote, private val cache: Cache) {
    private val dateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())

    fun getTrendingRepos(dateRange: Int, page: Int): Single<List<GitRepository>> {
        return remote.getTrendingPage(dateFormat.format(DateTime().minusDays(dateRange).toDate()), page)
                .doOnSuccess {
                    cache.add(it)
                }
    }

    fun getRepository(id: String): Single<GitRepository> {
        return cache.getRepository(id)
                .switchIfEmpty(remote.getRepository(id))
    }

    interface Remote {
        fun getRepository(id: String): Single<GitRepository>

        fun getTrendingPage(createdAfter: String, page: Int): Single<List<GitRepository>>
    }

    interface Cache {
        fun getRepository(id: String): Maybe<GitRepository>

        fun add(repositories: List<GitRepository>)
    }

}