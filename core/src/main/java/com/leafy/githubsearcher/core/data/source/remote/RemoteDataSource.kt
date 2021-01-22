package com.leafy.githubsearcher.core.data.source.remote

import com.leafy.githubsearcher.core.data.source.remote.network.ApiHeader
import com.leafy.githubsearcher.core.data.source.remote.network.ApiResponse
import com.leafy.githubsearcher.core.data.source.remote.network.ApiService
import com.leafy.githubsearcher.core.data.source.remote.response.detail.DetailResponse
import com.leafy.githubsearcher.core.data.source.remote.response.repo.RepositoryResponse
import com.leafy.githubsearcher.core.data.source.remote.response.username.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    private val headers = ApiHeader.getHeaders()

    suspend fun getSearchList(query: String): Flow<ApiResponse<List<UserResponse>>> =
        flow {
            try {
                val response = apiService.getSearchList(headers, query).items
                emit(
                    if (response.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty
                )
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetails(user: String): Flow<ApiResponse<DetailResponse>> =
        flow {
            try {
                val response = apiService.getDetails(headers, user)
                emit(
                    if (response != null) ApiResponse.Success(response) else ApiResponse.Error("Error: No entry has been found.")
                )
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getRepositoryList(user: String): Flow<ApiResponse<List<RepositoryResponse>>> =
        flow {
            try {
                val response = apiService.getRepositoryList(headers, user)
                emit(
                    if (response.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty
                )
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getFollowerList(user: String): Flow<ApiResponse<List<UserResponse>>> =
        flow {
            try {
                val response = apiService.getFollowerList(headers, user)
                emit(
                    if (response.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty
                )
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getFollowingList(user: String): Flow<ApiResponse<List<UserResponse>>> =
        flow {
            try {
                val response = apiService.getFollowingList(headers, user)
                emit(
                    if (response.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty
                )
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
}