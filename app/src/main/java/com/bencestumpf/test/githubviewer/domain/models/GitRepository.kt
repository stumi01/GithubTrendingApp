package com.bencestumpf.test.githubviewer.domain.models

data class GitRepository(val id: String,
                         val fullName: String, val name: String, val owner: String,
                         val description: String,
                         val language: String, val stars: Int)