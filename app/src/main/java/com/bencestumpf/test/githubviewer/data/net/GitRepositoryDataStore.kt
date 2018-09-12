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


    override fun getTrending(rangeInDays: Int): Single<List<GitRepository>> {
        return apiService.searchRepositories("stars", "desc", "topic:android")//, "created:>`date -v-7d '+%Y-%m-%d'`")
                .map {
                    if (it.isSuccessful) {
                        return@map it.body()?.items?.map(this::mapRepo)
                    }
                    throw NetworkErrorException("Cannot fetch the trending repositories")
                }
    }

    private fun mapRepo(model: RepositoryResponseModel): GitRepository =
            GitRepository(model.full_name,
                    model.description,
                    model.language?.let { it } ?: "",
                    model.stargazers_count?.let { it } ?: 0)

}