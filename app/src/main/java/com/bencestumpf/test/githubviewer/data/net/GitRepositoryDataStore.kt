package com.bencestumpf.test.githubviewer.data.net

import android.accounts.NetworkErrorException
import com.bencestumpf.test.githubviewer.data.net.services.GithubApiService
import com.bencestumpf.test.githubviewer.data.net.services.RepositoryResponseModel
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.domain.providers.GitRepositoryProvider
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitRepositoryDataStore @Inject constructor(private val apiService: GithubApiService) : GitRepositoryProvider.Remote {


    override fun getRepository(id: String): Single<GitRepository> {
        return apiService.getRepository(id)
                .map {
                    if (it.isSuccessful) {
                        return@map it.body()?.let(this::mapRepo)
                    }
                    throw NetworkErrorException("Cannot fetch the trending repositories")
                }
    }

    override fun getTrendingPage(createdAfter: String, page: Int): Single<List<GitRepository>> {
        return apiService.searchRepositoriesPage("stars", "desc", "topic:android+created:>$createdAfter", page)
                .map {
                    if (it.isSuccessful) {
                        return@map it.body()?.items?.map(this::mapRepo)
                    }
                    throw NetworkErrorException("Cannot fetch the trending repositories")
                }
    }

    private fun mapRepo(model: RepositoryResponseModel): GitRepository =
            GitRepository(model.id,
                    model.full_name,
                    model.name,
                    model.owner.login,
                    model.description?.let { it } ?: "",
                    model.language?.let { it } ?: "",
                    model.stargazers_count?.let { it } ?: 0,
                    model.forks?.let { it } ?: 0,
                    model.watchers?.let { it } ?: 0,
                    model.open_issues?.let { it } ?: 0,
                    model.created_at, model.updated_at,
                    model.html_url?.let { it } ?: "")

}