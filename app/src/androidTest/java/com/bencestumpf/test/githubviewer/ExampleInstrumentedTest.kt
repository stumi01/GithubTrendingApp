package com.bencestumpf.test.githubviewer

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.bencestumpf.test.githubviewer.di.Injector
import com.bencestumpf.test.githubviewer.di.compoents.AppComponent
import com.bencestumpf.test.githubviewer.di.modules.AppModule
import com.bencestumpf.test.githubviewer.di.modules.ProviderModule
import com.bencestumpf.test.githubviewer.domain.providers.GitRepositoryProvider
import com.bencestumpf.test.githubviewer.presentation.trending.TrendingActivity
import com.nhaarman.mockito_kotlin.mock
import it.cosenonjaviste.daggermock.DaggerMock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    fun espressoDaggerMockRule() = DaggerMock.rule<AppComponent>(AppModule(app), ProviderModule()) {
        set { component ->
            Injector.setComponent(component)
        }
    }

    val provider: GitRepositoryProvider = mock()

    val app: GithubViewerApplication get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as GithubViewerApplication


    @get:Rule
    val rule = espressoDaggerMockRule()

    @get:Rule
    val activityRule = ActivityTestRule(TrendingActivity::class.java, false, false)


    @Test
    fun useAppContext() {
        // Context of the app under test.
        activityRule.launchActivity(null)
        onView(withId(R.id.text)).check(matches(withText("Hello world")))

    }
}
