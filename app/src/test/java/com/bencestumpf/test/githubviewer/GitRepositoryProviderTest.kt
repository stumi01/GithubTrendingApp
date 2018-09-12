package com.bencestumpf.test.githubviewer

import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.domain.providers.GitRepositoryProvider
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.`when`
import java.util.*


class GitRepositoryProviderTest {

    private val remote: GitRepositoryProvider.Remote = mock()

    private val cache: GitRepositoryProvider.Cache = mock()

    private val provider: GitRepositoryProvider = GitRepositoryProvider(remote, cache)

    private val dummyRepository = GitRepository("id", "owner/reponame", "reponame", "owner",
            "This is my description", "Java", 5, 4, 3, 2,
            Date(), Date(), "https://github.com/stumi01/GithubTrendingApp")

    private val dummyRepository2 = GitRepository("id2", "owner/reponame", "reponame", "owner",
            "This is my description", "Java", 5, 4, 3, 2,
            Date(), Date(), "https://github.com/stumi01/GithubTrendingApp")


    @Test
    fun if_has_cache_use_it() {
        //Given
        val id = "testID"
        var result: GitRepository? = null

        `when`(cache.getRepository(id)).thenReturn(Maybe.just(dummyRepository))
        `when`(remote.getRepository(id)).thenReturn(Single.just(dummyRepository2))

        //When

        provider.getRepository(id).subscribe { model -> result = model }
        //Then
        verify(cache).getRepository(eq(id))
        assert(result == dummyRepository)
        assert(result != dummyRepository2)
    }

    @Test
    fun if_no_cache_use_remote() {
        //Given
        val id = "testID"
        var result: GitRepository? = null

        `when`(cache.getRepository(id)).thenReturn(Maybe.empty())
        `when`(remote.getRepository(id)).thenReturn(Single.just(dummyRepository2))

        //When

        provider.getRepository(id).subscribe { model -> result = model }

        //Then
        verify(cache).getRepository(eq(id))
        verify(remote).getRepository(eq(id))
        assert(result != dummyRepository)
        assert(result == dummyRepository2)
    }


    @Test
    fun getTrending_updates_cache() {
        //Given
        `when`(remote.getTrending(any())).thenReturn(Single.just(arrayListOf(dummyRepository, dummyRepository2)))

        //When
        provider.getTrendingRepos(7).subscribe()

        //Then
        verify(cache).add(any())
        verify(remote).getTrending(any())
    }

}
