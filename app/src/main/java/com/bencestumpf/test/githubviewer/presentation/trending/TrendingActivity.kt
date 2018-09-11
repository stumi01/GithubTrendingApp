package com.bencestumpf.test.githubviewer.presentation.trending

import android.os.Bundle
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

    @BindView(R.id.feature_trending_list)
    lateinit var trendingRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        supportActionBar?.setTitle(R.string.trending_repos)
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        presenter.detachView()
        super.onPause()
    }

    override fun showLoading() {
        //TODO
    }

    override fun showError() {
        //TODO
    }

    override fun showContent(repositories: List<GitRepository>) {
        val adapter = GitRepositoriesAdapter(this)
        adapter.setData(repositories)
        trendingRecyclerView.layoutManager = LinearLayoutManager(this)
        trendingRecyclerView.isNestedScrollingEnabled = false
        trendingRecyclerView.adapter = adapter
        trendingRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }
}