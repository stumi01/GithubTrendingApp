package com.bencestumpf.test.githubviewer.di.modules

import com.bencestumpf.test.githubviewer.data.cache.GitRepositoryCache
import com.bencestumpf.test.githubviewer.data.net.GitRepositoryDataStore
import com.bencestumpf.test.githubviewer.domain.providers.GitRepositoryProvider
import com.bencestumpf.test.githubviewer.helper.OpenClass
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@OpenClass
@Module
class ProviderModule {

    @Provides
    @Singleton
    fun provideGitRepositoryProvider(dataStore: GitRepositoryDataStore,
                                     cache: GitRepositoryCache): GitRepositoryProvider {
        return GitRepositoryProvider(dataStore, cache)
    }
}