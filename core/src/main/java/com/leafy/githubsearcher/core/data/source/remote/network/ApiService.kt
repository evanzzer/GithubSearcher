package com.leafy.githubsearcher.core.data.source.remote.network

import com.leafy.githubsearcher.core.data.source.remote.response.detail.DetailResponse
import com.leafy.githubsearcher.core.data.source.remote.response.repo.RepositoryResponse
import com.leafy.githubsearcher.core.data.source.remote.response.username.ListUserResponse
import com.leafy.githubsearcher.core.data.source.remote.response.username.UserResponse
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getSearchList(
        @HeaderMap headers: HashMap<String, String>,
        @Query("q") query: String
    ): ListUserResponse

    @GET("users/{username}")
    suspend fun getDetails(
        @HeaderMap headers: HashMap<String, String>,
        @Path("username") username: String
    ): DetailResponse?

    @GET("users/{username}/repos")
    suspend fun getRepositoryList(
        @HeaderMap headers: HashMap<String, String>,
        @Path("username") username: String
    ): List<RepositoryResponse>

    @GET("users/{username}/following")
    suspend fun getFollowingList(
        @HeaderMap headers: HashMap<String, String>,
        @Path("username") username: String
    ): List<UserResponse>

    @GET("users/{username}/followers")
    suspend fun getFollowerList(
        @HeaderMap headers: HashMap<String, String>,
        @Path("username") username: String
    ): List<UserResponse>
}