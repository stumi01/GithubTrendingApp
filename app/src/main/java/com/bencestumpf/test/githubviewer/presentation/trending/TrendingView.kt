package com.bencestumpf.test.githubviewer.presentation.trending

import android.support.v4.util.Pair
import android.view.View
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.presentation.common.MVPView

interface TrendingView : MVPView {
    fun showLoading()
    fun showError()
    fun showContent(repositories: List<GitRepository>)
    fun navigateToDetailsView(id: String, sharedViews: Array<Pair<View, String>>)
    fun dataArrived(repositories: List<GitRepository>)
    fun showNoMoreResultInfo()

}
