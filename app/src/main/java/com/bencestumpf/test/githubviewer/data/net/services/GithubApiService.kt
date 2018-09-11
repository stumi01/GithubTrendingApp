package com.bencestumpf.test.githubviewer.data.net.services

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface GithubApiService {

    @Headers("Content-Type: application/json")
    @GET("search/repositories")
    fun searchRepositories(@Query("sort") sort: String, @Query("order") order: String,
                           @Query("q") topic: String)
            : Single<Response<SearchResponseModel>>


}

data class SearchResponseModel(val total_count: Int, val items: List<com.bencestumpf.test.githubviewer.data.net.services.RepositoryResponseModel>)

data class RepositoryResponseModel(val id: String, val full_name: String, val description: String,
                                   val language: String?, val stargazers_count: Int, val html_url: String)
