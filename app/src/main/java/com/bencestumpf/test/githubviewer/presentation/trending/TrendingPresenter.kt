package com.bencestumpf.test.githubviewer.presentation.trending

import android.util.Log
import android.view.View
import com.bencestumpf.test.githubviewer.di.scopes.ActivityScope
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.domain.usecases.ObtainLatestTrendingRepos
import com.bencestumpf.test.githubviewer.presentation.common.MVPPresenter
import javax.inject.Inject

@ActivityScope
class TrendingPresenter @Inject constructor(private val obtainLatestTrendingRepos: ObtainLatestTrendingRepos) : MVPPresenter<TrendingView>() {

    private val TAG: String = TrendingPresenter::class.java.simpleName

    private var presentationModel: List<GitRepository>? = null


    private fun startLoading() {
        view?.showLoading()
        Log.d("STUMI", "startLoading")
        execute(obtainLatestTrendingRepos, this::onDataArrived, this::onError)
    }

    private fun onDataArrived(repositories: List<GitRepository>) {
        presentationModel = repositories
        Log.d("STUMI", "onDataArrived " + repositories?.size)
        view?.showContent(repositories)
    }

    private fun onError(error: Throwable) {
        Log.e(TAG, "Error during loading.", error)
        view?.showError()
    }

    fun onResume() {
        if (presentationModel == null) {
            startLoading()
        }
    }

    fun onRefresh() {
        startLoading()
    }

    fun onRepositoryClick(id: String, sharedViews: Array<android.support.v4.util.Pair<View, String>>) {
        view?.navigateToDetailsView(id, sharedViews)
    }

}
