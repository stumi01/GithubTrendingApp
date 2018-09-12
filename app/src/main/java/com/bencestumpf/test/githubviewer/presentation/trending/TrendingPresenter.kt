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

    private var presentationModel: ArrayList<GitRepository> = ArrayList()
    private var currentPage = 1

    private fun startLoading() {
        view?.showLoading()
        currentPage = 1
        presentationModel.clear()
        execute(obtainLatestTrendingRepos.withParams(currentPage), this::onDataArrived, this::onError)
    }

    private fun onDataArrived(repositories: List<GitRepository>) {
        presentationModel.addAll(repositories)
        view?.showContent(repositories)
    }

    private fun onError(error: Throwable) {
        Log.e(TAG, "Error during loading.", error)
        view?.showError()
    }

    fun onResume() {
        if (presentationModel.isEmpty()) {
            startLoading()
        }
    }

    fun onRefresh() {
        startLoading()
    }

    fun onRepositoryClick(id: String, sharedViews: Array<android.support.v4.util.Pair<View, String>>) {
        view?.navigateToDetailsView(id, sharedViews)
    }

    fun onLoadMore() {
        view?.showLoading()
        execute(obtainLatestTrendingRepos.withParams(++currentPage), this::onAdditionalDataArrived, this::onError)
    }

    private fun onAdditionalDataArrived(repositories: List<GitRepository>) {
        if (repositories.isEmpty()) {
            currentPage--
            view?.showNoMoreResultInfo()
        }
        presentationModel.addAll(repositories)
        view?.dataArrived(repositories)

    }
}
