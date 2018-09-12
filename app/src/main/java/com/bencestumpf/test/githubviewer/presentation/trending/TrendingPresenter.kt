package com.bencestumpf.test.githubviewer.presentation.trending

import android.util.Log
import com.bencestumpf.test.githubviewer.di.scopes.ActivityScope
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.domain.usecases.ObtainLatestTrendingRepos
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class TrendingPresenter @Inject constructor(private val obtainLatestTrendingRepos: ObtainLatestTrendingRepos) {

    private val TAG: String = TrendingPresenter::class.java.simpleName

    private var view: TrendingView? = null
    private var presentationModel: List<GitRepository>? = null

    private val subscriptions = ArrayList<Disposable>()

    fun attachView(view: TrendingView) {
        this.view = view
        startLoading()
    }

    private fun startLoading() {
        view?.showLoading()
        subscriptions += obtainLatestTrendingRepos.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataArrived, this::onError)
    }

    private fun onDataArrived(repositories: List<GitRepository>) {
        presentationModel = repositories
        view?.showContent(repositories)
    }

    private fun onError(error: Throwable) {
        Log.e(TAG, "Error during loading.", error)
        view?.showError()
    }

    fun detachView() {
        view = null
    }

    fun onPause() {
        subscriptions.forEach {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
        subscriptions.clear()
    }

    fun onResume() {
        if (presentationModel == null) {
            startLoading()
        }
    }

    fun onRefresh() {
        startLoading()
    }

}
