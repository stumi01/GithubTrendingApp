package com.bencestumpf.test.githubviewer.presentation.details

import com.bencestumpf.test.githubviewer.R
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.presentation.common.MVPActivity

class DetailsActivity : MVPActivity<DetailsPresenter, DetailsView>(), DetailsView {
    companion object {
        const val EXTRA_REPOSITORY_ID = "EXTRA_REPOSITORY_ID"
    }

    override fun setupView() {
        val repoId = intent.getStringExtra(EXTRA_REPOSITORY_ID)
        presenter.setup(repoId)
    }

    override fun getLayoutId(): Int = R.layout.activity_trending

    override fun getView(): DetailsView = this

    override fun showContent(repository: GitRepository) {
        supportActionBar?.title = repository.name
        supportActionBar?.subtitle = repository.owner
    }

}