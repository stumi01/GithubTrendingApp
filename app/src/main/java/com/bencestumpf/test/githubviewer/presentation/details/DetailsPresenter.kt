package com.bencestumpf.test.githubviewer.presentation.details

import android.util.Log
import com.bencestumpf.test.githubviewer.di.scopes.ActivityScope
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.domain.usecases.ObtainRepoDetails
import com.bencestumpf.test.githubviewer.presentation.common.MVPPresenter
import javax.inject.Inject

@ActivityScope
class DetailsPresenter @Inject constructor(private val obtainRepoDetails: ObtainRepoDetails) : MVPPresenter<DetailsView>() {

    private val TAG: String = DetailsPresenter::class.java.simpleName

    private var repoId: String? = null


    fun setup(repoId: String?) {
        this.repoId = repoId
        repoId?.let {
            execute(obtainRepoDetails.withParams(it), this::onDataArrived, this::onError)
        }
    }

    private fun onDataArrived(repository: GitRepository) {
        view?.showContent(repository)
    }

    private fun onError(error: Throwable) {
        Log.e(TAG, "Error during loading.", error)
    }
}
