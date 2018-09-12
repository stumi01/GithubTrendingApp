package com.bencestumpf.test.githubviewer.domain.usecases

import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.domain.providers.GitRepositoryProvider
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObtainRepoDetails @Inject constructor(private val repositoryProvider: GitRepositoryProvider)
    : Usecase<GitRepository> {

    lateinit var id: String

    override fun getSubscribable(): Single<GitRepository> {
        return repositoryProvider.getRepository(id)
    }

    fun withParams(id: String): ObtainRepoDetails {
        this.id = id
        return this
    }
}
