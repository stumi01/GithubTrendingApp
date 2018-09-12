package com.bencestumpf.test.githubviewer.presentation.trending

import com.bencestumpf.test.githubviewer.domain.models.GitRepository

interface TrendingView {
    fun showLoading()
    fun showError()
    fun showContent(repositories: List<GitRepository>)
    fun navigateToDetailsView(id: String)

}
