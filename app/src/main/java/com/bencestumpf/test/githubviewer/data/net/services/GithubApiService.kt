package com.bencestumpf.test.githubviewer.data.net.services

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


interface GithubApiService {

    @Headers("Content-Type: application/json")
    @GET("search/repositories")
    fun searchRepositories(@Query("sort") sort: String, @Query("order") order: String,
                           @Query(value = "q", encoded = true) query: String)
            : Single<Response<SearchResponseModel>>

    @Headers("Content-Type: application/json")
    @GET("search/repositories")
    fun searchRepositoriesPage(@Query("sort") sort: String, @Query("order") order: String,
                               @Query(value = "q", encoded = true) query: String, @Query("page") page: Int)
            : Single<Response<SearchResponseModel>>

    @Headers("Content-Type: application/json")
    @GET("repositories/{id}")
    fun getRepository(@Path("id") id: String): Single<Response<RepositoryResponseModel>>


}

data class SearchResponseModel(val total_count: Int, val items: List<com.bencestumpf.test.githubviewer.data.net.services.RepositoryResponseModel>)

data class RepositoryResponseModel(val id: String, val full_name: String, val name: String,
                                   val description: String?, val owner: Owner,
                                   val language: String?, val stargazers_count: Int?,
                                   val forks: Int?, val watchers: Int?,
                                   val created_at: Date, val updated_at: Date,
                                   val open_issues: Int?,
                                   val license: License?,
                                   val html_url: String?)

data class License(val key: String)
data class Owner(val login: String)
