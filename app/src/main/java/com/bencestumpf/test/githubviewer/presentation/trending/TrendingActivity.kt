package com.bencestumpf.test.githubviewer.presentation.trending

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bencestumpf.test.githubviewer.R
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import dagger.android.AndroidInjection
import javax.inject.Inject


class TrendingActivity : AppCompatActivity(), TrendingView {
    @Inject
    lateinit var presenter: TrendingPresenter

    @BindView(R.id.trending_swipeRefresh)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.trending_list)
    lateinit var trendingRecyclerView: RecyclerView

    private lateinit var adapter: GitRepositoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        supportActionBar?.setTitle(R.string.trending_repos)
        presenter.attachView(this)
        setupSwipeToRefresh()
        setupRecycler()
    }

    private fun setupRecycler() {
        adapter = GitRepositoriesAdapter(this)
        trendingRecyclerView.layoutManager = LinearLayoutManager(this)
        trendingRecyclerView.isNestedScrollingEnabled = false
        trendingRecyclerView.adapter = adapter
        trendingRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun setupSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(presenter::onRefresh)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun showError() {
        //TODO
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showContent(repositories: List<GitRepository>) {
        adapter.setData(repositories)
        swipeRefreshLayout.isRefreshing = false
    }
}