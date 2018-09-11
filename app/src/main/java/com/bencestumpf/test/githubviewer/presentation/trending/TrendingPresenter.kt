package com.bencestumpf.test.githubviewer.presentation.trending

import android.util.Log
import com.bencestumpf.test.githubviewer.di.scopes.ActivityScope
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.domain.usecases.ObtainLatestTrendingRepos
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class TrendingPresenter @Inject constructor(private val obtainLatestTrendingRepos: ObtainLatestTrendingRepos) {

    private var view: TrendingView? = null

    fun attachView(view: TrendingView) {
        this.view = view
        startLoading()
    }

    private fun startLoading() {
        view?.showLoading()
        obtainLatestTrendingRepos.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataArrived, this::onError)
    }

    private fun onDataArrived(repositories: List<GitRepository>) {
        view?.showContent(repositories)
    }

    private val TAG: String = TrendingPresenter::class.java.simpleName

    private fun onError(error: Throwable) {
        Log.e(TAG, "Error during loading.", error)
        view?.showError()
    }

    fun detachView() {
        view = null
    }

}
