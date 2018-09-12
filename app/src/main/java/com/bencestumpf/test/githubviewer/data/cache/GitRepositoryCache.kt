package com.bencestumpf.test.githubviewer.data.cache

import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.domain.providers.GitRepositoryProvider
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitRepositoryCache @Inject constructor() : GitRepositoryProvider.Cache {
    private val repositoryMap = HashMap<String, GitRepository>()

    override fun add(repositories: List<GitRepository>) {
        repositories.forEach { repositoryMap[it.id] = it }
    }

    override fun getRepository(id: String): Maybe<GitRepository> {
        return Maybe.create { emitter ->
            repositoryMap[id]?.let { emitter.onSuccess(it) } ?: emitter.onComplete()
        }
    }
}