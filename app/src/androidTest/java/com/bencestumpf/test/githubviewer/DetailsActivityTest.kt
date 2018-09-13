package com.bencestumpf.test.githubviewer

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.domain.providers.GitRepositoryProvider
import com.bencestumpf.test.githubviewer.helper.espressoDaggerMockRule
import com.bencestumpf.test.githubviewer.presentation.details.DetailsActivity
import com.bencestumpf.test.githubviewer.presentation.details.DetailsActivity.Companion.EXTRA_REPOSITORY_ID
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.lang.RuntimeException
import java.util.*


@RunWith(AndroidJUnit4::class)
class DetailsActivityTest {

    private val provider: GitRepositoryProvider = mock()

    @get:Rule
    val rule = espressoDaggerMockRule()

    @get:Rule
    val activityRule = ActivityTestRule(DetailsActivity::class.java, false, false)

    private val dummyRepository = GitRepository("id", "owner/reponame", "reponame", "owner",
            "This is my description", "Java", 5, 4, 3, 2,
            Date(), Date(), "https://github.com/stumi01/GithubTrendingApp")

    private lateinit var intent: Intent

    @Before
    fun setUp() {
        Mockito.`when`(provider.getRepository(eq(dummyRepository.id))).thenReturn(Single.just(dummyRepository))

        intent = Intent().apply {
            putExtra(EXTRA_REPOSITORY_ID, dummyRepository.id)
        }
    }

    @Test
    fun details_shows_correctly() {
        //Given

        //When
        activityRule.launchActivity(intent)

        //Then
        Espresso.onView(ViewMatchers.withId(R.id.details_language)).check(ViewAssertions.matches(ViewMatchers.withText(dummyRepository.language)))
        Espresso.onView(ViewMatchers.withId(R.id.details_forks)).check(ViewAssertions.matches(ViewMatchers.withText(dummyRepository.forks.toString())))
        Espresso.onView(ViewMatchers.withId(R.id.details_watchers)).check(ViewAssertions.matches(ViewMatchers.withText(dummyRepository.watchers.toString())))
        Espresso.onView(ViewMatchers.withId(R.id.details_stars)).check(ViewAssertions.matches(ViewMatchers.withText(dummyRepository.stars.toString())))
        Espresso.onView(ViewMatchers.withId(R.id.details_issues)).check(ViewAssertions.matches(ViewMatchers.withText(activityRule.activity.getString(R.string.issues, dummyRepository.issues))))
        Espresso.onView(ViewMatchers.withId(R.id.details_description)).check(ViewAssertions.matches(ViewMatchers.withText(dummyRepository.description)))

    }

    @Test
    fun view_on_browser_navigates_out_of_the_app() {
        //Given
        val expectedIntent = allOf(hasAction(Intent.ACTION_VIEW), hasData(dummyRepository.url))

        //When
        activityRule.launchActivity(intent)
        Intents.init()
        intending(not(isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
        Espresso.onView(ViewMatchers.withId(R.id.details_readme)).perform(ViewActions.click())

        //Then
        intended(expectedIntent)
        Intents.release()
    }

    @Test
    fun when_error_happens_show_error_view_retry_button_invokes_reload() {
        //Given
        Mockito.`when`(provider.getRepository(eq(dummyRepository.id))).thenReturn(Single.error(RuntimeException("Test exception")))

        //When
        activityRule.launchActivity(intent)

        //Then
        verify(provider, times(1)).getRepository(eq(dummyRepository.id))
        Espresso.onView(ViewMatchers.withId(R.id.error_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //When
        Espresso.onView(ViewMatchers.withId(R.id.retry_button)).perform(ViewActions.click())

        //Then
        verify(provider, times(2)).getRepository(eq(dummyRepository.id))
    }
}