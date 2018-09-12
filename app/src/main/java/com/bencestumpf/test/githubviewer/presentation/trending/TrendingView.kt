package com.bencestumpf.test.githubviewer.presentation.trending

import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.presentation.common.MVPView

interface TrendingView : MVPView {
    fun showLoading()
    fun showError()
    fun showContent(repositories: List<GitRepository>)
    fun navigateToDetailsView(id: String)

}
