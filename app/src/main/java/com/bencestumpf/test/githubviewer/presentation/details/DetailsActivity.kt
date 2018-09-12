package com.bencestumpf.test.githubviewer.presentation.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bencestumpf.test.githubviewer.R

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPOSITORY_ID = "EXTRA_REPOSITORY_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)
        val repoId = intent.getStringExtra(EXTRA_REPOSITORY_ID)

    }
}