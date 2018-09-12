package com.bencestumpf.test.githubviewer.presentation.details

import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.presentation.common.MVPView

interface DetailsView : MVPView {

    fun showContent(repository: GitRepository)
    fun navigateToUrl(url: String)
    fun showError()

}
