package com.bencestumpf.test.githubviewer.presentation.details

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.bencestumpf.test.githubviewer.R
import com.bencestumpf.test.githubviewer.di.Injector
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.presentation.common.MVPActivity
import org.ocpsoft.prettytime.PrettyTime


class DetailsActivity : MVPActivity<DetailsPresenter, DetailsView>(), DetailsView {
    companion object {

        const val EXTRA_REPOSITORY_ID = "EXTRA_REPOSITORY_ID"

    }

    @BindView(R.id.error_view)
    lateinit var errorView: View
    @BindView(R.id.details_content)
    lateinit var content: View
    @BindView(R.id.details_language)
    lateinit var language: TextView
    @BindView(R.id.details_forks)
    lateinit var forks: TextView
    @BindView(R.id.details_stars)
    lateinit var stars: TextView
    @BindView(R.id.details_watchers)
    lateinit var watchers: TextView
    @BindView(R.id.details_issues)
    lateinit var issues: TextView
    @BindView(R.id.details_created)
    lateinit var createdAt: TextView
    @BindView(R.id.details_last_commit)
    lateinit var updatedAt: TextView
    @BindView(R.id.details_description)
    lateinit var description: TextView
    @BindView(R.id.details_readme)
    lateinit var openView: View


    override fun injectDependencies() {
        Injector.getAppComponent()
                .detailsComponent()
                .inject(this)
    }

    override fun setupView() {
        val repoId = intent.getStringExtra(EXTRA_REPOSITORY_ID)
        presenter.setup(repoId)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_navigate_before_white_48)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun getLayoutId(): Int = R.layout.activity_details

    override fun getView(): DetailsView = this

    override fun showContent(repository: GitRepository) {
        supportActionBar?.title = repository.name
        supportActionBar?.subtitle = repository.owner

        language.text = repository.language
        forks.text = repository.forks.toString()
        stars.text = repository.stars.toString()
        watchers.text = repository.watchers.toString()
        issues.text = getString(R.string.issues, repository.issues.toString())
        description.text = repository.description
        val p = PrettyTime()
        createdAt.text = getString(R.string.created_at, p.format(repository.createdAt))
        updatedAt.text = getString(R.string.updated_at, p.format(repository.updatedAt))

        openView.setOnClickListener { presenter.onRepoUrlClick(repository.url) }

        errorView.visibility = View.GONE
        content.visibility = View.VISIBLE
    }

    override fun navigateToUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    override fun showError() {
        content.visibility = View.GONE
        errorView.visibility = View.VISIBLE
    }

    @OnClick(R.id.retry_button)
    fun onRetryClick() {
        presenter.onRetry()
    }

}