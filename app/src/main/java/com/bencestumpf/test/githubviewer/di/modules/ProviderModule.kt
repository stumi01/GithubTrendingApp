package com.bencestumpf.test.githubviewer.di.modules

import com.bencestumpf.test.githubviewer.data.net.GitRepositoryDataStore
import com.bencestumpf.test.githubviewer.domain.providers.GitRepositoryProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProviderModule {

    @Provides
    @Singleton
    fun provideGitRepositoryProvider(dataStore: GitRepositoryDataStore): GitRepositoryProvider {
        return GitRepositoryProvider(dataStore)
    }
}