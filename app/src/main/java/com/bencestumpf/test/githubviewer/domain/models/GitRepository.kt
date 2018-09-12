package com.bencestumpf.test.githubviewer.domain.models

import java.util.*

data class GitRepository(val id: String,
                         val fullName: String, val name: String, val owner: String,
                         val description: String,
                         val language: String, val stars: Int, val forks: Int, val watchers: Int,
                         val issues: Int,
                         val createdAt: Date, val updatedAt: Date,
                         val url: String)