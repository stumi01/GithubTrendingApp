package com.bencestumpf.test.githubviewer

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.domain.providers.GitRepositoryProvider
import com.bencestumpf.test.githubviewer.helper.ChildMatcher.hasChildren
import com.bencestumpf.test.githubviewer.helper.espressoDaggerMockRule
import com.bencestumpf.test.githubviewer.presentation.trending.TrendingActivity
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import java.lang.RuntimeException
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TrendingActivityTest {

    private val provider: GitRepositoryProvider = mock()

    @get:Rule
    val rule = espressoDaggerMockRule()

    @get:Rule
    val activityRule = ActivityTestRule(TrendingActivity::class.java, false, false)

    private val dummyRepository = GitRepository("id", "owner/reponame", "reponame", "owner",
            "This is my description", "Java", 5, 4, 3, 2,
            Date(), Date(), "https://github.com/stumi01/GithubTrendingApp")

    private val dummyRepository2 = GitRepository("id2", "owner/reponame", "reponame", "owner",
            "This is my description", "Java", 5, 4, 3, 2,
            Date(), Date(), "https://github.com/stumi01/GithubTrendingApp")


    @Test
    fun when_error_happens_show_error_view_retry_button_invokes_reload() {
        //Given
        `when`(provider.getTrendingRepos(any(), eq(1))).thenReturn(Single.error(RuntimeException("Test exception")))

        //When
        activityRule.launchActivity(null)

        //Then
        verify(provider, times(1)).getTrendingRepos(any(), eq(1))
        onView(withId(R.id.error_view)).check(matches(isDisplayed()))

        //When
        onView(withId(R.id.retry_button)).perform(click())

        //Then
        verify(provider, times(2)).getTrendingRepos(any(), eq(1))
    }

    @Test
    fun fetched_result_shows_correctly() {
        //Given
        `when`(provider.getTrendingRepos(any(), eq(1))).thenReturn(Single.just(arrayListOf(dummyRepository)))

        //When
        activityRule.launchActivity(null)

        //Then
        verify(provider, times(1)).getTrendingRepos(any(), eq(1))
        onView(withId(R.id.error_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.trending_list)).check(matches(hasChildren(`is`(2))))//repo row and load more row
        onView(withId(R.id.repository_name)).check(matches(withText(dummyRepository.fullName)))
        onView(withId(R.id.repository_language)).check(matches(withText(dummyRepository.language)))
        onView(withId(R.id.repository_description)).check(matches(withText(dummyRepository.description)))
        onView(withId(R.id.repository_stars)).check(matches(withText(dummyRepository.stars.toString())))
    }

    @Test
    fun click_on_row_navigates_to_details_activity() {
        //Given
        `when`(provider.getTrendingRepos(any(), eq(1))).thenReturn(Single.just(arrayListOf(dummyRepository)))
        `when`(provider.getRepository(eq(dummyRepository.id))).thenReturn(Single.just(dummyRepository))

        //When
        activityRule.launchActivity(null)

        //Then
        verify(provider, times(1)).getTrendingRepos(any(), eq(1))
        onView(withId(R.id.repository_name)).perform(click())
        verify(provider, times(1)).getRepository(eq(dummyRepository.id))
        onView(withId(R.id.details_content)).check(matches(isDisplayed()))
    }

    @Test
    fun load_more_button_loads_next_page_and_its_added_to_the_view() {
        //Given
        `when`(provider.getTrendingRepos(any(), eq(1))).thenReturn(Single.just(arrayListOf(dummyRepository)))
        `when`(provider.getTrendingRepos(any(), eq(2))).thenReturn(Single.just(arrayListOf(dummyRepository2)))
        //When
        activityRule.launchActivity(null)

        //Then
        verify(provider, times(1)).getTrendingRepos(any(), eq(1))
        onView(withId(R.id.trending_list)).check(matches(hasChildren(`is`(2))))//repo row and load more row
        onView(withId(R.id.load_more)).perform(click())
        onView(withId(R.id.trending_list)).check(matches(hasChildren(`is`(3))))//2 repo row and load more row
        verify(provider, times(1)).getTrendingRepos(any(), eq(2))

    }

}
