package com.bencestumpf.test.githubviewer.presentation.trending

import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.bencestumpf.test.githubviewer.R
import com.bencestumpf.test.githubviewer.di.Injector
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.bencestumpf.test.githubviewer.presentation.common.MVPActivity
import com.bencestumpf.test.githubviewer.presentation.details.DetailsActivity
import com.bencestumpf.test.githubviewer.presentation.details.DetailsActivity.Companion.EXTRA_REPOSITORY_ID


class TrendingActivity : MVPActivity<TrendingPresenter, TrendingView>(), TrendingView {

    @BindView(R.id.trending_swipeRefresh)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.trending_list)
    lateinit var trendingRecyclerView: RecyclerView

    @BindView(R.id.error_view)
    lateinit var errorView: View

    private lateinit var adapter: GitRepositoriesAdapter

    override fun injectDependencies() {
        Injector.getAppComponent()
                .trendingComponent()
                .inject(this)
    }

    override fun setupView() {
        supportActionBar?.setTitle(R.string.trending_repos)
        setupSwipeToRefresh()
        setupRecycler()
    }

    override fun getLayoutId(): Int = R.layout.activity_trending

    override fun getView(): TrendingView = this

    private fun setupRecycler() {
        adapter = GitRepositoriesAdapter(this, presenter::onRepositoryClick, presenter::onLoadMore)
        trendingRecyclerView.layoutManager = LinearLayoutManager(this)
        trendingRecyclerView.isNestedScrollingEnabled = false
        trendingRecyclerView.adapter = adapter
        trendingRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun setupSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(presenter::onRefresh)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
        errorView.visibility = View.GONE
        swipeRefreshLayout.visibility = View.VISIBLE
    }

    override fun showError() {
        swipeRefreshLayout.isRefreshing = false
        errorView.visibility = View.VISIBLE
        swipeRefreshLayout.visibility = View.GONE
    }

    override fun showContent(repositories: List<GitRepository>) {
        adapter.setData(repositories)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun dataArrived(repositories: List<GitRepository>) {
        adapter.addData(repositories)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun navigateToDetailsView(id: String, sharedViews: Array<Pair<View, String>>) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *sharedViews)

        this.startActivity(Intent(this, DetailsActivity::class.java).apply {
            putExtra(EXTRA_REPOSITORY_ID, id)
        }, options.toBundle())
    }

    @OnClick(R.id.retry_button)
    fun onRetryClick() {
        presenter.onRefresh()
    }

    override fun showNoMoreResultInfo() {
        Toast.makeText(this, R.string.no_more_results,
                Toast.LENGTH_LONG).show();
    }

}
