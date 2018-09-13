package com.bencestumpf.test.githubviewer.helper

import android.support.test.InstrumentationRegistry
import com.bencestumpf.test.githubviewer.GithubViewerApplication
import com.bencestumpf.test.githubviewer.di.Injector
import com.bencestumpf.test.githubviewer.di.compoents.AppComponent
import com.bencestumpf.test.githubviewer.di.modules.AppModule
import com.bencestumpf.test.githubviewer.di.modules.ProviderModule
import it.cosenonjaviste.daggermock.DaggerMock

fun espressoDaggerMockRule() = DaggerMock.rule<AppComponent>(AppModule(app), ProviderModule()) {
    set { component ->
        Injector.setComponent(component)
    }
}

private val app: GithubViewerApplication get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as GithubViewerApplication

