package com.bencestumpf.test.githubviewer.presentation.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class MVPActivity<P : MVPPresenter<V>, V : MVPView> : AppCompatActivity() {

    @Inject
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        ButterKnife.bind(this)
        setupView()
        presenter.attachView(getView())
    }

    abstract fun setupView()

    abstract fun getLayoutId(): Int

    abstract fun getView(): V

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}